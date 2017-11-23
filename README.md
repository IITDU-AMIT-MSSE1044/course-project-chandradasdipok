# Mining Historical Issue Repositories

This repository is the implementation of the proposed mining-based approach by [Ding et al 2014](https://www.researchgate.net/profile/Tao_Xie13/publication/269692153_Mining_Historical_Issue_Repositories_to_Heal_Large-Scale_Online_Service_Systems/links/550c7db80cf2128741612908.pdf)

## 1. The Proposed Mining Based Approach 
Online  service  systems  like  online  banking,  e-commerce,  and  email  services  are most popular and important. So, the availability of the online systems are top priority now. Although some substantial works in eradicating the failure of the systems, the failure rate is significant.So, when a failure i.e., issue happens, the online systems tend to reduce the Mean Time To Reduce (MTTR). For reducing the MTTR, a manual approach is to find an appropriate healing action for an issue. But manually identifying an appropriate healing action is time consuming and error prone. There are two problems in manually identifying appropriate healing action. First, there are huge number of data to analyze. Second, there needs expert domain knowledge. So, reduce time consuming and error proneness, the common approach is mining based for suggesting an appropriate healing action for a given issue. Our approach generates a signature for an issue from its corresponding transaction logs and then retrieves historical issues  with similar signatures from a historical issue repository. The historical issue repository records the solved historical issues. Each issue has  a number of basic attributes: affected time, affected location (e.g., specific cluster, network, or data-center), real customer impact measurement, corresponding transaction logs, etc., along with the appropriate healing  action  taken  by  operators  to  heal  the  issue.

The mining-based approach suffers from two challenges named high-correlation phenomenon and weak-discrimination phenomenon. The high-correlation phenomenon refers to the  correlation of event’s  occurrences  in  transaction logs for causing ineffective historical-issue  retrieval. The weak-discrimination  phenomenon  refers to noisy events that appear relatively independent to the transaction  status. The authors have proposed technique of concept analysis to address the high-correlation phenomenon and the technique of contrast  analysis  to  address the weak-discrimination phenomenon. The approach is as follows: First,  they have used concept  analysis  and  contrast  analysis  to  generate  the signature  for  an  issue. Second,  they have retrieved  historical issues  similar  to  the  given  new  issue  from  an  issue repository  based  on  their  generated  signatures.  Third, they have produced healing suggestions by adapting the healing actions of the retrieved historical issues.

### 1.1 Signature Generation
In Signature Generation, For Concept Analysis, they have used Formal Concept Analysis (FCA) to group highly correlated events as intents of concept. For Contrast Analysis, they have used Mutual Information Calculation (MI) to  measure  the  correlation  between  each concept  and  its  corresponding  transaction  status,  and then evaluates the complementary set of intents between parent and child concepts in concept lattice by measuring their Delta Mutual Information (DMI). They generate the  signature  for  the  issue  as  the  complementary  sets that satisfy the predefined criterion. 

### 1.2 Similar-Issue Retrieval
In Similar-Issue Retrieval, the issues are represented as vector. And the cosine similarity is used to measure the similarity between two documents. 

### 1.3 Healing-Suggestion Adaptation
In Healing-Suggestion Adaptation, the authors have stored some predefined healing suggestion in triple structure <verb, target, location>. The verb and target of a given issue will be the verb and target of the retrieved historical issue. The location is retrieved from the transaction logs of the given issue.

They have named the approach as "Ours". They have compared their approach with others two approach named "App1" and "App2". In App1 ,  They have not address the high-correlation phenomenon: they calculate Mutual Information of each individual event as its weight (using contrast info), then represent the events as a vector, and finally calculate the cosine score as the similarity metric value. In App2 , They have not address the weak-discrimination  phenomenon:  they  first apply FCA and use delta events between parent and child concepts to  define  terms  (using  grouping  information), use TF-IDF as the  weight  of  each  term,  and  finally  calculate the cosine core as the similarity metric value.

The authors have conducted experiment on selected 243 issues from a system named ServiceX (name is not disclosed for security reason). The authors has considered two scenario named ScenarioA and Scenario B. In ScenarioA, they have considered each issue as new issue and the previously encountered issues as historical issues. And in ScenarioB, they have considered each issue as new issue and issues other than the given issue as historical issues. The overall accuracy in ScenarioA of Ours, App1 and App2 is 87%,  82%  and 72% respectively. In ScenarioB, the  highest  precision of proposed approach is 0.87, with the corresponding similarity threshold as 0.85.

## 2. The Implementation

The project has implemented the approaches "Ours", "App1" and "App2" on branches [proposed](https://github.com/IITDU-AMIT-MSSE1044/course-project-chandradasdipok/tree/proposed), [app1](https://github.com/IITDU-AMIT-MSSE1044/course-project-chandradasdipok/tree/app1) and [app2](https://github.com/IITDU-AMIT-MSSE1044/course-project-chandradasdipok/tree/app2) respectively. For Concept Analysis, Formal Concept Lattice is built following  "The Next Closure Algorithm" proposed by [Ganter <i>et al</i> 2010](https://link.springer.com/chapter/10.1007%2F978-3-642-11928-6_22?LI=true). 


The project have also implemented the thumbs rule of mining-based approach on branch [general](https://github.com/IITDU-AMIT-MSSE1044/course-project-chandradasdipok/tree/general). In "general" approach, the issue is represented as collection of events. The weight is the failure probability of each event. Then the similarity score between event vectors are measured. 

The generalize of the above mentioned approaches is shown in Figure 1.<br/>
<img src="https://github.com/IITDU-AMIT-MSSE1044/course-project-chandradasdipok/blob/master/mining_approach.png" alt="mining approach" align="center"/>
<p align="center"> Figure 1: Mining Approach </p>

As shown Figure 1 the approaches retrieve a historical issue for a given issue. And the healing action of historical issue is the healing suggestion of the given issue.

## 3. The Data

The authors have conducted experiment their approaches in 243 issues of a system named ServiceX. The data of the paper as well as similar kind of data is not available in public. So, a set of dummy data have to be prepared. The data are prepared by the class of [DataSetGenerator.java](https://github.com/IITDU-AMIT-MSSE1044/course-project-chandradasdipok/blob/app2/src/com/geet/mining/dataset/DataSetGenerator.java). There is an assumption that a hypothetical system has events named "a", "b", "c", "d","e","x1","x2"."x3","x4","x5","x6","x7","x8","y1","y2","p","q","k" and "z". There are sevent modules T1, T2, T3, T4, T5, T6 and T7. The modules are T1 = {a,b,x1,x2,x3,x4,x5,x6,x7,x8,z}, T2 = {a,b,p,z}, T3 = {a,b,q,z}, T4 = {a,b,k,z}, T5 = {a,b,c,d,e,y1,z}, T6 = {a,b,c,d,e,y2,z} and  T7 = {a,b,c,d,e,z}.

A Transaction is represented as a Transaction Class which has fields timestamp, transactionID, event, logs and transactionStatus. Here for simplication, the status of transactions is stored as fail or succeed. An issue is prepared by executing randomly with random number of modules and random status of each transaction. The logs are ignored because of the scope of the project.

## 4. How to Use
Prerequisites : Git, Java(TM) SE Runtime Environment (build 1.8.0_131-b11), Ecplise Mars

### 4.1 Create as a Java Project 

#### 4.1.1 Clone the repository 
  ````
  git clone https://github.com/IITDU-AMIT-MSSE1044/course-project-chandradasdipok/ 
  ````
#### 4.1.2 Import the repository in Eclipse as Java Project

### 4.2 Run the application

##### 4.2.1 Working in [General](https://github.com/IITDU-AMIT-MSSE1044/course-project-chandradasdipok/tree/general) Branch
1. Right Click on Project and then <b>Team->Switch To->general</b>
2. Go to [Experiment.java](https://github.com/IITDU-AMIT-MSSE1044/course-project-chandradasdipok/blob/general/src/com/geet/mining/experiment/Experiment.java) and </b>Run</b> the file.

##### 4.2.2 Working in [App1](https://github.com/IITDU-AMIT-MSSE1044/course-project-chandradasdipok/tree/app1) Branch
1. Right Click on Project and then <b>Team->Switch To->app1</b>
2. Go to [Experiment.java](https://github.com/IITDU-AMIT-MSSE1044/course-project-chandradasdipok/blob/app1/src/com/geet/mining/experiment/Experiment.java) and </b>Run</b> the file.


##### 4.2.3 Working in [App2](https://github.com/IITDU-AMIT-MSSE1044/course-project-chandradasdipok/tree/app2) Branch
1. Right Click on Project and then <b>Team->Switch To->app2</b>
2. Go to [Experiment.java](https://github.com/IITDU-AMIT-MSSE1044/course-project-chandradasdipok/blob/app2/src/com/geet/mining/experiment/Experiment.java) and </b>Run</b> the file.


##### 4.2.4 Working in [Proposed](https://github.com/IITDU-AMIT-MSSE1044/course-project-chandradasdipok/tree/proposed) Branch
1. Right Click on Project and then <b>Team->Switch To->proposed</b>
2. Go to [Experiment.java](https://github.com/IITDU-AMIT-MSSE1044/course-project-chandradasdipok/blob/proposed/src/com/geet/mining/experiment/Experiment.java) and </b>Run</b> the file.

#### 4.3 Output
In each case, a Contingency Table of issue's similarity score will be shown in the output as follows:
`````````
1.0,0.38416284034898157,0.260750569764919,0.5462313816629312,0.13050936413860945,0.5304810496221232,0.36298781168106387,0.08863336205058582,0.2992272207474969,0.4603566989034448,
0.38416284034898157,1.0,0.5252752148636969,0.4630407553359154,0.34492937580539645,0.20179359598372845,0.3042909968972491,0.45234179077180203,0.5645367946463894,0.3163666481075452,
0.260750569764919,0.5252752148636969,1.0000000000000002,0.2858554440227762,0.1732108382087304,0.26453898124066216,0.16864673825805004,0.8498163383605812,0.7492471102216047,0.5733288258743081,
0.5462313816629312,0.4630407553359154,0.2858554440227762,0.9999999999999998,0.37506322079322263,0.48708894681718035,0.23428602760751321,0.13279445232589845,0.49584744596922253,0.22603103724685997,
0.13050936413860945,0.34492937580539645,0.1732108382087304,0.37506322079322263,1.0,0.22872402701968034,0.10701677593411944,0.2574613187856368,0.22108296982175993,0.05276580205983033,
0.5304810496221232,0.20179359598372845,0.26453898124066216,0.48708894681718035,0.22872402701968034,1.0,0.16224239375689972,0.139358609383654,0.24193401259834874,0.3517576091608404,
0.36298781168106387,0.3042909968972491,0.16864673825805004,0.23428602760751321,0.10701677593411944,0.16224239375689972,0.9999999999999998,0.09088700055227145,0.15677787876098517,0.19844548628539752,
0.08863336205058582,0.45234179077180203,0.8498163383605812,0.13279445232589845,0.2574613187856368,0.139358609383654,0.09088700055227145,1.0,0.552666518344453,0.5588256610055237,
0.2992272207474969,0.5645367946463894,0.7492471102216047,0.49584744596922253,0.22108296982175993,0.24193401259834874,0.15677787876098517,0.552666518344453,1.0,0.45404263543562506,
0.4603566989034448,0.3163666481075452,0.5733288258743081,0.22603103724685997,0.05276580205983033,0.3517576091608404,0.19844548628539752,0.5588256610055237,0.45404263543562506,1.0,
`````````


## 5. Trade-Offs

The data are prepared randomly by as much as possible. The project don't implement fully the healing suggestion adaptation. Unlike extracting verb and target from a table of healing suggestion prepared by experts, and location from the transaction logs, the project considers correcly retrieved historical issue as hit.     


## 6. Helpful Links

* For Construction Formal Concept Analysis
  ** [Formal Concept Analysis](http://www.math.tu-dresden.de/~ganter/psfiles/FingerExercises.pdf) 
  ** [Two basic algorithms in concept analysis](https://link.springer.com/chapter/10.1007%2F978-3-642-11928-6_22?LI=true) 
  ** [Coursera Lecture](https://www.coursera.org/learn/formal-concept-analysis/lecture/jI7Ne/closures-in-lectic-order)
* For Mutual Information
  ** [Information Gain](https://www.autonlab.org/_media/tutorials/infogain11.pdf)
  ** [Mutual Information](http://www.surdeanu.info/mihai/teaching/ista555-spring15/readings/yang97comparative.pdf)

## 7. Challenges
The main challenge to implement the proposed approach is to extract the Formal Concepts using Formal Concept Analysis. Beacuse of huge number of objects and huge number of attributes represented by transactions and events respectively, the number of Formal Concepts can be huge. The best approach to construct the formal concept is "Next Closure Algorithm" proposed by Ganter. The time complexity for generating all formal concepts is O(|G||C||M|) where G is objects, M is attributes, and C is formal concepts. The alogorithm is implemented in [chandradasdipok/formal_concept_analysis](https://github.com/chandradasdipok/formal_concept_analysis). The implementation is also a by-product of this project.


