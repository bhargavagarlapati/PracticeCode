import os
import string
import re

from selenium import webdriver
from selenium.webdriver.edge.service import Service as Edge_Service
from selenium.webdriver.edge.options import Options as Edge_Options
from selenium.webdriver.chrome.service import Service as Chrome_Service
from selenium.webdriver.chrome.options import Options as Chrome_Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.action_chains import ActionChains
import tkinter as tk

class TimeSheetValidator:
    url = "https://jira.ncr.com/secure/Tempo.jspa#/my-work/week?type=LIST"
    days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"]
    Error_List = []
    elements = []
    driver: None
    browser=""

    def __init__(self):

        root = tk.Tk()
        root.title("Browser Selection")
        root.geometry("300x200+500+100")
        var = tk.StringVar()
        var.set("Edge")

        tk.Label(root, text="Pick one Browser \n", font=('Times New Roman', 17, 'bold'), fg='blue').pack()
        tk.Radiobutton(root, text="Chrome", variable=var, value="Chrome", font=('Times New Roman', 16, 'bold'),
                       fg='peachpuff4').pack()
        tk.Radiobutton(root, text="Edge", variable=var, value="Edge", font=('Times New Roman', 17, 'bold'),
                       fg='brown').pack()
        tk.Label(root, text="").pack()
        button = tk.Button(root, text="Submit", command=root.destroy, font=('Times New Roman', 15), fg='white',
                           bg='green')
        button.pack()
        root.mainloop()
        self.browser=var.get()

        if self.browser == "Edge":
            os.system("taskkill /f /im  msedge.exe")
            options = Edge_Options()
            options.add_experimental_option("detach", True)
            options.add_argument(f"--user-data-dir=C:\\Users\\{os.getlogin()}\\AppData\\Local\\Microsoft\\Edge\\User Data")
            options.add_argument("--profile-directory=Default")
            options.add_experimental_option("excludeSwitches", ['enable-automation'])
            self.driver = webdriver.Edge(service=Edge_Service(), options=options)

        elif self.browser == "Chrome":
            os.system("taskkill /f /im  chrome.exe")
            options = Chrome_Options()
            options.add_experimental_option("detach", True)
            options.add_argument(f"--user-data-dir=C:\\Users\\{os.getlogin()}\\AppData\\Local\\Google\\Chrome\\User Data")
            options.add_argument("--profile-directory=Default")
            options.add_experimental_option("excludeSwitches", ['enable-automation'])
            self.driver = webdriver.Chrome(service=Chrome_Service(), options=options)

        self.driver.get(self.url)
        self.driver.maximize_window()
        self._waitforelement(self.driver,"//div[@id='canvas-days']/div[@name='calendarListViewDay']")
        self._displaymessage("\n\nPress Validate after Filling Time Sheet\n\n", "Validate", "400x200+1100+40")
        self.elements = self.driver.find_elements(By.XPATH, "//div[@id='canvas-days']/div[@name='calendarListViewDay']")

    def _validate(self,account, task, i, jira_id):
        if (account == "INTERNAL - Internal Issues Account" and task != "Task for Internal/Admin Issues Only"):
                self.Error_List.append(f"Corrected the Task: \"{task}\" to \"Internal/Admin\" for \"{jira_id}\" logged on {self.days[i]}")
                self._correcttask()

        elif (task == "Task for Internal/Admin Issues Only" and account != "INTERNAL - Internal Issues Account"):
            self._mismatch(account,task,jira_id,self.days[i])

        elif (re.search("^.*SWM.*$", account) and not re.search("^.*SWM.*$", task)):
            self._mismatch(account, task, jira_id, self.days[i])

        elif (re.search("^.*PS.*$", account) and not re.search("^.*PS.*$", task)):
            self._mismatch(account,task,jira_id,self.days[i])

        elif (re.search("^.*R&D.*$", account) and not re.search("^.*R&D.*$", task)):
            self._mismatch(account,task,jira_id,self.days[i])

        else:
            self.driver.find_element(By.XPATH, "//span[contains(text(),'Cancel')]").click()

    def _mismatch(self,account,task,jira_id,day):
        self.Error_List.append(
            f"Mismatch between Account: \"{account}\" \n and Task: \"{task}\" for \"{jira_id}\" logged on {day}")
        self.driver.find_element(By.XPATH, "//span[contains(text(),'Cancel')]").click()

    def _correcttask(self):
        self.driver.find_element(By.XPATH,
                                 "//div[contains(@id,'Task')]//div[contains(@class,'singleValue')]/div").click()
        task = self.driver.find_element(By.XPATH,
                                        "//div[contains(@id,'Task')]//div[contains(@class,'singleValue')]/following-sibling::div/input")

        for character in "Task for Internal/Admin Issues Only":
            task.send_keys(character)

        task.send_keys(Keys.ENTER)
        self.driver.find_element(By.XPATH, "//button[@name='submitWorklogButton']").click()
        self._nwaitforelement(self.driver,self.driver.find_element(By.XPATH, "//button[@name='submitWorklogButton']"))

    def timesheet_validator(self):
            iteration_count = 0
            actions = ActionChains(self.driver)
            for i in range(5):
                count = 0
                self.work_ids = self.elements[i].find_elements(By.NAME, "tempoWorklogCard")
                if (len(self.work_ids) != 0):
                    for j in range(len(self.work_ids)):
                        iteration_count = iteration_count+1
                        #if self.work_ids[j].is_displayed() == False:self.driver.execute_script("arguments[0].scrollIntoView(true);",self.work_ids[j])
                        val = self.driver.find_element(By.XPATH,f"(//div[@name='tempoCardIssueKey']/a)[{iteration_count}]")
                        if not val.is_displayed():self.driver.execute_script("arguments[0].scrollIntoView(true);",val)
                        jira_id = self.driver.find_element(By.XPATH,f"(//div[@name='tempoCardIssueKey']/a)[{iteration_count}]").text
                        time = self.driver.find_element(By.XPATH,f"(//span[@name='tempoCardDuration'])[{iteration_count}]").text
                        count = count + self._totalminutes(time)
                        self.work_ids[j].click()
                        self._waitforelement(self.driver,"//div[contains(@id,'Account')]//div[contains(@class,'singleValue')]/div")
                        self.account = self.driver.find_element(By.XPATH,
                                                      "//div[contains(@id,'Account')]//div[contains(@class,'singleValue')]/div").text
                        self.task = self.driver.find_element(By.XPATH,
                                                   "//div[contains(@id,'Task')]//div[contains(@class,'singleValue')]/div").text
                        self._validate(self.account, self.task, i, jira_id)

                    if(count>480):
                         self.Error_List.append(f"Number of hours Logged are greater than 8 on {self.days[i]}")
                    elif(count<480):
                        self.Error_List.append(f"Number of hours Logged are less than 8 on {self.days[i]}")

                elif(len(self.work_ids)==0):
                    self.Error_List.append(f"No Hours are logged for {self.days[i]}")

                actions.key_down(Keys.LEFT_CONTROL).send_keys(Keys.HOME).perform()

            #self.driver.quit()

            if (len(self.Error_List) == 0):
                self._displaymessage("No Errors: Success\n\n","Ok","400x200+100+40")
            else:
                output = "\n\n".join(list(map(lambda x: str(self.Error_List.index(x) + 1) + ". " + x, self.Error_List)))
                self._displaymessage(output+"\n\n","Ok","1000x400+300+40")

    def _totalminutes(self,time):
        totalminutes = None

        if (re.search("^.*h.*m$", time)):
            hours = int(time.split("h")[0])
            minutes = int(time.split("h")[1].split("m")[0])
            totalminutes = (hours * 60) + minutes

        elif (re.search("^.*h$", time)):
            totalminutes = (int(time.split("h")[0])) * 60

        elif (re.search("^.*m$", time)):
            totalminutes = int(time.split("m")[0])

        return totalminutes

    def _waitforelement(self,driver: webdriver, xpath: string):
        wait = WebDriverWait(driver, 360)
        wait.until(EC.visibility_of_element_located((By.XPATH, xpath)))
        wait.until(EC.element_to_be_clickable(self.driver.find_element(By.XPATH,xpath)))

    def _nwaitforelement(self,driver: webdriver, element):
        wait = WebDriverWait(driver, 60)
        wait.until(EC.invisibility_of_element(element))

    def _displaymessage(self,displaytext, buttonname, dimensions):
        root = tk.Tk()
        root.withdraw()

        MsgBox = tk.Toplevel(root)
        MsgBox.title("Message")
        MsgBox.geometry(dimensions)
        message = tk.Label(MsgBox, text=displaytext, font=('Calibri', 14))
        message.pack()
        button = tk.Button(MsgBox, text=buttonname, command=root.destroy, width=10, height=1,
                           font=('Times New Roman', 14),
                           fg='white', bg='green')
        button.pack()
        MsgBox.protocol('WM_DELETE_WINDOW', root.destroy)
        MsgBox.mainloop()

x = TimeSheetValidator()
x.timesheet_validator()