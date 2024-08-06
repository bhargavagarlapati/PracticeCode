from lxml import etree
import filecmp
import os

dir1= os.path.join(os.getcwd(),"Old")
dir2= os.path.join(os.getcwd(),"New")

result = filecmp.dircmp(dir1,dir2)

def compare_xml_files(file1, file2):
    tree1 = etree.parse(file1)
    tree2 = etree.parse(file2)
    root1 = tree1.getroot()
    root2 = tree2.getroot()
    differences = []
    compare_elements(root1, root2, differences)
    return differences

def getdifftag(elem1,elem2):
    val = len(elem2) - len(elem1)
    rtag = []
    if val > 0:
        for i in range(0, len(elem1)):
            if ((elem2[i].tag != elem1[i].tag) and (len(rtag) < val)):
                rtag.append(elem2[i])
        if len(rtag) == 0:
            rtag.append(elem2[len(elem2) - 1])

    elif val<0:
        for i in range(0,len(elem2)):
            if((elem1[i].tag!=elem2[i].tag) and (len(rtag)<abs(val))):
                rtag.append(elem1[i])
        if len(rtag)==0:
            rtag.append(elem1[len(elem1)-1])

    return rtag

def removedifftags(elem1,elem2,differences):
    val = len(elem2)-len(elem1)
    rtag = getdifftag(elem1,elem2)

    for ele in rtag:
        if val > 0:
            if len(ele)>0:differences.insert(0,f"Section Missing: Section with Tag {ele.tag} is not present in Old Version")
            else:differences.insert(0,f"Tag Missing: Tag {ele.tag} is not present in Old Agent Version")
            elem2.remove(ele)
        elif val < 0:
            if len(ele)>0:differences.insert(0,f"Critical: Section Missing: Section with Tag {ele.tag} is not present in New Version")
            else:differences.insert(0,f"Tag Missing: Tag {ele.tag} is not present in New Agent Version")
            elem1.remove(ele)

    return elem1,elem2,differences

def compare_elements(elem1, elem2, differences):

        if(elem1.tag == elem2.tag) and (len(elem1)!=len(elem2)):
            elem1,elem2,differences = removedifftags(elem1,elem2,differences)
            compare_elements(elem1,elem2,differences)

        elif len(elem1)==len(elem2):
            if elem1.tag == elem2.tag and elem1.text != elem2.text:
                differences.append(f"Tag data differs: {elem1.tag}({elem1.text})!={elem2.tag}({elem2.text})")

            if elem1.tag != elem2.tag:
                differences.append(f"Tags differ: {elem1.tag} != {elem2.tag}")

            for child1, child2 in zip(elem1, elem2):
                compare_elements(child1, child2, differences)

with open("result.txt", "w") as file:
    file.write(f"Total number of processed files: {len(result.same_files)+len(result.diff_files)}\n")
    file.write(f"Total number of identical files: {len(result.same_files)}\n")
    file.write(f"Total number of files with differences: {len(result.diff_files)}\n\n")
    if len(result.diff_files)>0:
        for diffile in result.diff_files:
            file.write(f"Differences for the file {diffile}:\n")
            file.write('\n'.join(compare_xml_files(dir1+"\\"+diffile, dir2+"\\"+diffile)))
            file.write('\n\n')