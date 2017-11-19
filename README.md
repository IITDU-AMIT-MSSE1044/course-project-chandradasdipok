# Mining Historical Issue Repositories

This repository is the implementation of the proposed mining-based approach by [Ding et al 2014](https://www.researchgate.net/profile/Tao_Xie13/publication/269692153_Mining_Historical_Issue_Repositories_to_Heal_Large-Scale_Online_Service_Systems/links/550c7db80cf2128741612908.pdf)

## The Proposed Mining Based Approach 
Online  service  systems  like  online  banking,  e-commerce,  and  email  services  are most popular and important. So, the availability of the online systems are top priority now. Although some substantial works in eradicating the failure of the systems, the failure rate is significant.So, when a failure i.e., issue happens, the online systems tend to reduce the Mean Time To Reduce (MTTR). For reducing the MTTR, a manual approach is to find an appropriate healing action for an issue. But manually identifying an appropriate healing action is time consuming and error prone. There are two problems in manually identifying appropriate healing action. First, there are huge number of data to analyze. Second, there needs expert domain knowledge. So, reduce time consuming and error proneness, the common approach is mining based for suggesting an appropriate healing action for a given issue. Our approach generates a signature for an issue from its corresponding transaction logs and then retrieves historical issues  with similar signatures from a historical issue repository. The historical issue repository records the solved historical issues. Each issue has  a number of basic attributes: affected time, affected location (e.g., specific cluster, network, or data-center), real customer impact measurement, corresponding transaction logs, etc., along with the appropriate healing  action  taken  by  operators  to  heal  the  issue.

The mining-based approach suffers from two challenges named high-correlation phenomenon and weak-discrimination phenomenon. The high-correlation phenomenon refers to the  correlation of event’s  occurrences  in  transaction logs for causing ineffective historical-issue  retrieval. The weak-discrimination  phenomenon  refers to noisy events that appear relatively independent to the transaction  status. The authors have proposed technique of concept analysis to address the high-correlation phenomenon and the technique of contrast  analysis  to  address the weak-discrimination phenomenon. The approach is as follows: First,  they have used concept  analysis  and  contrast  analysis  to  generate  the signature  for  an  issue. Second,  they have retrieved  historical issues  similar  to  the  given  new  issue  from  an  issue repository  based  on  their  generated  signatures.  Third, they have produced healing suggestions by adapting the healing actions of the retrieved historical issues.

### Signature Generation
In Signature Generation, For Concept Analysis, they have used Formal Concept Analysis (FCA) to group highly correlated events as intents of concept. For Contrast Analysis, they have used Mutual Information Calculation (MI) to  measure  the  correlation  between  each concept  and  its  corresponding  transaction  status,  and then evaluates the complementary set of intents between parent and child concepts in concept lattice by measuring their Delta Mutual Information (DMI). They generate the  signature  for  the  issue  as  the  complementary  sets that satisfy the predefined criterion. 

### Similar-Issue Retrieval
In Similar-Issue Retrieval, the issues are represented as vector. And the cosine similarity is used to measure the similarity between two documents. 

### Healing-Suggestion Adaptation
In Healing-Suggestion Adaptation, the authors have stored some predefined healing suggestion in triple structure <verb, target, location>. The verb and target of a given issue will be the verb and target of the retrieved historical issue. The location is retrieved from the transaction logs of the given issue.

They have named the approach as "Ours". They have compared their approach with others two approach named "App1" and "App2". In App1 ,  They have not address the high-correlation phenomenon: they calculate Mutual Information of each individual event as its weight (using contrast info), then represent the events as a vector, and finally calculate the cosine score as the similarity metric value. In App2 , They have not address the weak-discrimination  phenomenon:  they  first apply FCA and use delta events between parent and child concepts to  define  terms  (using  grouping  information), use TF-IDF as the  weight  of  each  term,  and  finally  calculate the cosine core as the similarity metric value.

The authors have conducted experiment on selected 243 issues from a system named ServiceX (name is not disclosed for security reason). The authors has considered two scenario named ScenarioA and Scenario B. In ScenarioA, they have considered each issue as new issue and the previously encountered issues as historical issues. And in ScenarioB, they have considered each issue as new issue and issues other than the given issue as historical issues. The overall accuracy in ScenarioA of Ours, App1 and App2 is 87%,  82%  and 72% respectively. In ScenarioB, the  highest  precision of proposed approach is 0.87, with the corresponding similarity threshold as 0.85.

## The Implementation

The project has implemented the approaches "Ours", "App1" and "App2" on branches [proposed](https://github.com/IITDU-AMIT-MSSE1044/course-project-chandradasdipok/tree/proposed), [app1](https://github.com/IITDU-AMIT-MSSE1044/course-project-chandradasdipok/tree/app1) and [app2](https://github.com/IITDU-AMIT-MSSE1044/course-project-chandradasdipok/tree/app2) respectively. For Concept Analysis, Formal Concept Lattice is built following  "The Next Closure Algorithm" proposed by [Ganter <i>et al</i> 2010](https://link.springer.com/chapter/10.1007%2F978-3-642-11928-6_22?LI=true). 


The project have also implemented the thumbs rule of mining-based approach on branch [general](https://github.com/IITDU-AMIT-MSSE1044/course-project-chandradasdipok/tree/general). In "general" approach, the issue is represented as collection of events. The weight is the failure probability of each event. Then the similarity score between event vectors are measured. 

The generalize of the above mentioned approaches is shown in Figure 1.<br/>
<img src="https://github.com/IITDU-AMIT-MSSE1044/course-project-chandradasdipok/blob/master/mining_approach.png" alt="mining approach" align="center"/>
<p align="center"> Figure 1: Mining Approach </p>

As shown Figure 1 the approaches retrieve a historical issue for a given issue. And the healing action of historical issue is the healing suggestion of the given issue.

## The Data

The authors have conducted experiment their approaches in 243 issues of a system named ServiceX. The data of the paper as well as similar kind of data is not available in public. So, a set of dummy data have to be prepared. The data are prepared by the class of [DataSetGenerator.java](https://github.com/IITDU-AMIT-MSSE1044/course-project-chandradasdipok/blob/app2/src/com/geet/mining/dataset/DataSetGenerator.java). There is an assumption that a hypothetical system has events named "a", "b", "c", "d","e","x1","x2"."x3","x4","x5","x6","x7","x8","y1","y2","p","q","k" and "z". There are sevent modules T1, T2, T3, T4, T5, T6 and T7. The modules are T1 = {a,b,x1,x2,x3,x4,x5,x6,x7,x8,z}, T2 = {a,b,p,z}, T3 = {a,b,q,z}, T4 = {a,b,k,z}, T5 = {a,b,c,d,e,y1,z}, T6 = {a,b,c,d,e,y2,z} and  T7 = {a,b,c,d,e,z}.

A Transaction is represented as a Transaction Class which has fields timestamp, transactionID, event, logs and transactionStatus. Here for simplication, the status of transactions is stored as fail or succeed. An issue is prepared by executing randomly with random number of modules and random status of each transaction. The logs are ignored because of the scope of the project.

## Trade-Offs

The data are prepared randomly by as much as possible. The project don't implement fully the healing suggestion adaptation. Unlike extracting verb and target from a table of healing suggestion prepared by experts, and location from the transaction logs, the project considers correcly retrieved historical issue as hit.     


