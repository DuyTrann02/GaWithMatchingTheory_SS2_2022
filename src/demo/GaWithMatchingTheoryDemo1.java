package demo;
/**
 * @author Vũ Đình Duy _ 1901040047
 */
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

import demo.GaleShapleyAlgorithm;

public class GaWithMatchingTheoryDemo1 {
	
	public static InputDataDriver inputDataDriver = new InputDataDriver("src/demo/newData.xlsx");
	public static List<Student> studentList = inputDataDriver.getStudentList();
	public static List<School> schoolList = inputDataDriver.getSchoolList();
	public static List<Conflict> conflictData = inputDataDriver.getConflictSet();
	

	 public static class Demo extends AbstractProblem{
		 
	        public Demo(){
	            super(studentList.size(),2);//super(numberOfVariables, numberOfObject)
	        } 
	        /*
	         * bubble sort function
	         */
	        static void bubbleSort(float arr[], int n) {
	            int i, j;
	            float temp;
	            boolean swapped;
	            for (i = 0; i < n - 1; i++) {
	                swapped = false;
	                for (j = 0; j < n - i - 1; j++) {
	                    if (arr[j] > arr[j + 1]) {
	                        // swap arr[j] và arr[j+1]
	                        temp = arr[j];
	                        arr[j] = arr[j + 1];
	                        arr[j + 1] = temp;
	                        swapped = true;
	                    }
	                }
                    /*
                     * Is there still element for swapping?
                     *       continue loop 
                     *       :
                     *       break 
                     */
	                if (swapped == false)
	                    break;
	            }
	        }
	        /*
	         * Find max  function
	         *    return max element of array and index of this element in array
	         *    return {max, index}.
	         */
	        static int[] findMax(int[] arr) {
	        	int max =arr[0];
	        	int index = 0;
	        	for(int i=0;i<arr.length;i++) {
	        		if(arr[i]>max) {
	        			max=arr[i];
	        			index=i;
	        		}
	        	}
	        	int[] arr1 = {max,index};
	            
	        	return  arr1;
	        }
	        /*
	         * Find min element and its index in array
	         * 
	         */
	        static int findMin(float[] arr) {
	        	float min = arr[0];
	        	int index = 0;
	        	for(int i=0;i<arr.length;i++) {
	        		if(arr[i]<min) {
	        			min = arr[i];
	        			index = i;
	        		}
	        	}
	        	return index;
	        }
	        /*
        	 * Return index of element in array
        	 */
		    static int findIndex(String e, String[] arr) {
		    	int index=-1;
		    	for(int i=0;i<arr.length;i++) {
		    		if(e.equals(arr[i])) {
		    			index = i;
		    		}
		    	}
		    	return index;
		    }
	        /*
	         * Function for counting frequency of element in array
	         *     
	         */
	        static int countFrequency(String e, String[] list ) {
	        	int frequency = 0;
	        	for(int i=0;i<list.length;i++) {
	        		if (list[i]==e) {
	        			frequency++;
	        		}
	        	}
	        	return frequency;
	        }
	        /*
	         * Function for get a column form 2 dimensional array
	         */
	        static String[] getColumn(String[][] list, int index) {
	        	String[] result = new String[list[0].length];
	        	for(int i=0;i<list[0].length;i++) {
	        		result[i] = list[i][index];
	        	}
	        	return result;
	        }
	        /*
	         * Function for get a row from 2 dimensional array
	         */
	        static String[] getRow(String[][] list, int index) {
	        	String[] result = new String[list[0].length];
	        	for(int i=0;i<list[0].length;i++) {
	        		result[i] = list[index][i];
	        	}
	        	return result;
	        }
	        /*
	         * Function for get a row from 2 dimensional array
	         */
	        static int[] getRow(int[][] list, int index) {
	        	int[] result = new int[list.length];
	        	for(int i=0;i<list.length;i++) {
	        		result[i] = list[index][i];
	        	}
	        	return result;
	        }
	        /*
	         * Function to count score satisfy for location
	         */
	        static int getIndexOfSatisfyLocation(String locationStudent, String locationSchool) {
	        	int locationStu =0;
	        	int locationSch =0;
	        	if(locationStudent == "North") {
	        		locationStu = 1;
	        	}else if (locationStudent =="Middle"){
	        		locationStu = 2;
	        	}else {
	        		locationStu = 3;
	        	}
	        	
	        	if(locationSchool == "North") {
	        		locationSch = 1;
	        	}else if (locationSchool =="Middle"){
	        		locationSch = 2;
	        	}else {
	        		locationSch = 3;
	        	}
	        	
	        	int count = Math.abs(locationSch - locationStu);
	        	 
	            if(count == 0) {
	            	return schoolList.size();
	            }else if(count ==1) {
	            	return schoolList.size()+1;
	            }else {
	            	return schoolList.size()+2;
	            }
	        	
	        }
	        
	        /*
	         * Function for counting avg score of a group of student
	         */
	        static float getAvgScore(ArrayList<Student> list) {
	        	float result = (float) 0.0;
	        	float total = (float) 0.0;
	        	for(int i=0;i<list.size();i++) {
	        		total = total + list.get(i).getMark();
	        	}
	        	result = total/list.size();
	        	return result;
	        }
		    /*
		     * Count average priority of a student group.
		     */
	        public static String[] countAvgPriority(ArrayList<Student> group) {
	        	//avgPriority or result 
	        	String[] avgPriority = new String[schoolList.size()];
	        	/*
	        	 * create 2-dimension array to store all priorities of all student in input group
	        	 *         allPriorities[number of priorities][number of student]
	        	 *         => each column is priority of one student
	        	 *         and each row are priorities of 1st priority, 2nd priority, 3rd priority,...
	        	 */
	        	if(group!=null) {
	        		String[][] prioritiesList = new String[schoolList.size()][group.size()];
		        	//loop each student in group
		        	for(int i=0;i<group.size();i++) {
		        		//get priority of each student in group
		        		String[] prio = group.get(i).getPriorities();
		        		//loop priorities of each student
		        		for( int k = 0; k<prio.length;k++) {
		        			prioritiesList[k][i] = prio[k];
		        		}
		        	}
		        	
		        	/*
		        	 * create 2-dimension array to store frequency of each school in each priority (1st priority, 2nd priority, 3rd priority,...)
		        	 *      frequencyList[number of school][size of priority list]
		        	 *      => each row are frequencies of one school in all priority  
		        	 *      and each column are frequencies of all school in each priority
		        	 */     
		        	int[][] frequencyList = new int[schoolList.size()][group.get(0).getPriorities().length];
		        	for(int i=0;i<schoolList.size();i++) {
		        		for(int j =0; j<group.get(0).getPriorities().length;j++) {
		        			frequencyList[i][j] = countFrequency(schoolList.get(i).getSchoolName(),getRow(prioritiesList,j));
		        		}
		        	}
		        	
		        	/*
		        	 * Get avgPriority
		        	 */
		        	for(int i=0;i<schoolList.size();i++) {
		        		int[] prio = findMax(getRow(frequencyList,i));
		        		avgPriority[prio[1]] = schoolList.get(i).getSchoolName();
		        		for(int j=i;j<schoolList.size();j++) {
		        			frequencyList[j][prio[1]] = -1;
		        		}
		        	}
		        	
		        	
	        		return avgPriority;
	        	}
	        	return null;
        	}
	        /*
	         * Function for returning average of school
	         */
	        public static String[] countSchoolPriority(float[] avgScore, float standardScore) {
	        	//avgPriority or result 
	        	int[] avgPriority = new int[schoolList.size()];
	        	//constraint between standardScore with each avgScore of each group
	        	float[] constraintScore = new float[avgScore.length];
	        	for(int i=0;i<avgScore.length;i++) {
	        		constraintScore[i] = Math.abs(standardScore-avgScore[i]);
	        	}
	        	for(int i=0;i<avgPriority.length;i++) {
	        		avgPriority[i] = findMin(constraintScore);
	        		constraintScore[findMin(constraintScore)] = 11;
	        	}
	        	
	        	//Convert to String arr
	        	String[] avgPriorityInString = new String[schoolList.size()];
	        	for(int i=0;i<avgPriority.length;i++) {
	        		avgPriorityInString[i] = "Group " + String.valueOf(avgPriority[i]);
	        	}
	        	return avgPriorityInString;
	        }
	        
	        @Override
	        public void evaluate(Solution solution) {
	        	
	        	//get variables
	        	/*
	        	 * Variables is randomly from 0 to 2
	        	 * Number variables same as number of student
	        	 * 
	        	 */
	        	List<Integer> variablesList = new ArrayList<>();
			    for(int i=0;i<studentList.size();i++) {
			    	variablesList.add(Integer.parseInt(solution.getVariable(i).toString()));
			    }
	        	
	        	/*
	        	 * Define a list contain all group.
	        	 * Numbers student of each group is randomly   
	        	 */
			    
			    ArrayList<ArrayList<Student> > listGroup = new ArrayList<ArrayList<Student>>(schoolList.size());
			    for(int i=0;i<schoolList.size();i++) {
			    	ArrayList<Student> arr = new ArrayList<Student>();
			    	listGroup.add(arr);
			    }
			    
	        	for(int i=0;i<variablesList.size();i++) {
	        		for(int j=0;j<schoolList.size();j++) {
	        			if(variablesList.get(i)==j) {
	        				listGroup.get(j).add(studentList.get(i));
	        			}
	        		}
	        	}
	        	
	        	/*
	        	 * Define input for Gale-Shapley algorithm
	        	 *       average priorities of each group student: studentPriority[][]
	        	 *       priorities of each school :               schoolOfPriority[][]
	        	 */
	        	//student priority.
	        	String[][] studentPriority = new String[schoolList.size()][schoolList.size()];
	        	for(int i=0;i<listGroup.size();i++) {
	        		if(listGroup.get(i).size()!=0) {
	        			String[] avgGroup = countAvgPriority(listGroup.get(i));
	        			for(int j=0;j<avgGroup.length;j++) {
	        				studentPriority[i][j] = avgGroup[j];
	        			}
	        		}else {
	        			for(int j=0;j<schoolList.size();j++) {
	        				studentPriority[i][j] = schoolList.get(j).getSchoolName();
	        			}
	        		}
	        	}
	        	
	        	
	        	//School priority 
              	String[][] schoolPriority = new String[schoolList.size()][schoolList.size()];
              	//avgScore arr of groups
              	float[] avgScoreArr = new float[schoolList.size()];
          		for(int j = 0; j<avgScoreArr.length;j++) {
          			avgScoreArr[j]= getAvgScore(listGroup.get(j));
          		}
          		//count school priority
              	for(int i=0;i<schoolList.size();i++) {
              		   String[] schoolPrio = countSchoolPriority(avgScoreArr, schoolList.get(i).getStandardScore());
              		   for(int j=0;j<schoolList.size();j++) {
              			   schoolPriority[i][j] = schoolPrio[j];
              		   }
              	}
	        	
	        	/*
	        	 * Apply Gale-Shapley
	        	 */
	        	String[] listStudentGroup = new String[listGroup.size()];
	        	for(int i=0;i<listStudentGroup.length;i++){
	        		listStudentGroup[i] = "Group " + String.valueOf(i);
	        	}

	        	String[] listSchool = new String[schoolList.size()];
	        	for(int i=0;i<listSchool.length;i++) {
	        		listSchool[i] = schoolList.get(i).getSchoolName();
	        	}
	        	GaleShapleyAlgorithm count = new GaleShapleyAlgorithm(listStudentGroup,listSchool,studentPriority,schoolPriority);
	        	
	        	//System.out.println(" ");
	        	
	        	//for(int i=0;i<schoolList.size();i++) {
	        		//System.out.print(count.getSchoolMatchingGroup(listStudentGroup[i])+": ");
	        		//for(int j=0;j<listGroup.get(i).size();j++) {
	        			//System.out.print(listGroup.get(i).get(j).getStudentId() + " ");
	        		//}
	        		//System.out.println(" ");
	        	//}
	        	//System.out.println(" ");
	        	//Count satisfy point after matching
	        	
	        	//STUDENT SATISFY
	        	//Satisfy for priority of student
	        	float totalSatisfyPriority=(float) 0;
	        	//Satisfy for location of student
	        	float totalSatisfyLocation=(float) 0;
	        	//Satisfy for fee of student
	        	float totalSatisfyFee =(float) 0;
	        	//Loop each group
	        	for(int i=0;i<listGroup.size();i++) {
	        		//loop each student of each group
	        		for(int j=0;j<listGroup.get(i).size();j++) {
	        			
	        			//PRIORITY
	        			//get priority of student
	        			String[] priorityOfEachStudent = listGroup.get(i).get(j).getPriorities();
	        			//count school that student elected is on what in list prefer
	        			int indexConflictScore = findIndex(count.getSchoolMatchingGroup(listStudentGroup[i]),priorityOfEachStudent);
	        			//find index in conflict set and add to totalSastifyPriority
	        			totalSatisfyPriority = totalSatisfyPriority +conflictData.get(0).getConflictDataSet()[indexConflictScore];
	        			
	        			//LOCATION
	        			//get location of student 
	        			String locationStudent = listGroup.get(i).get(j).getLocaction();
	        			//get location of school
	        			String locationSchool = schoolList.get(findIndex(count.getSchoolMatchingGroup(listStudentGroup[i]),listSchool)).getPosition();
	        			//count score for location by conflict data and add to totalSatisfyLocation
	        			totalSatisfyLocation = totalSatisfyLocation + conflictData.get(0).getConflictDataSet()[getIndexOfSatisfyLocation(locationStudent, locationSchool)];
	        			
	        			//FEE
	        			//get fee expectation of student
	        			float feeExpect = listGroup.get(i).get(j).getFeeExpectation();
	        			//get fee of school
	        			float feeSchool = schoolList.get(findIndex(count.getSchoolMatchingGroup(listStudentGroup[i]),listSchool)).getFees();
	        			//count satisfy fee of student by formula conflict/(abs(feeExpect - feeSchool)). It means The smaller the difference, the higher the score
	        			if((Math.abs(totalSatisfyFee-feeExpect))>1) {
	        				totalSatisfyFee = totalSatisfyFee + conflictData.get(0).getConflictDataSet()[schoolList.size()+3]/((Math.abs((feeSchool-feeExpect))));
	        			}else {
	        				totalSatisfyFee = totalSatisfyFee + 10;
	        			}
	        			
	        		}
	        	}
	        	
	        	//SCHOOL SATISFY
	        	//Satisfy for number of student target
	        	float totalSatisfyNumberOfStudentTarget =0;
	        	//Satisfy for different between standard score and average score of student group
	        	float totalSatisfyStandardScore = 0;
	        	//loop each school
	        	for(int i=0;i<schoolList.size();i++) {
	        		//loop each student of each school
	      
	        			//NUMBER OF STUDENT TARGET
	        			//get numberOfStudentTarget of school
	        			int numStuTarget  = schoolList.get(findIndex(count.getSchoolMatchingGroup(listStudentGroup[i]),listSchool)).getNumberStudentTarget();
	        			//get size of student group
	        			int sizeGroup = listGroup.get(i).size();
	        			//count satisfy by formula conflict/(abs(numberOfStudentTarget - sizeGroup)). It means The smaller the difference, the higher the score
	        			if(Math.abs(numStuTarget-sizeGroup)>1) {
	        				totalSatisfyNumberOfStudentTarget = totalSatisfyNumberOfStudentTarget + conflictData.get(1).getConflictDataSet()[1]/(Math.abs(numStuTarget-sizeGroup));
	        				if(totalSatisfyFee==Float.POSITIVE_INFINITY) {
	        	        		totalSatisfyFee = (float) 5.0;
	        	        	}
	        			}else {
	        				totalSatisfyNumberOfStudentTarget = totalSatisfyNumberOfStudentTarget*20/100;
	        				if(totalSatisfyFee==Float.POSITIVE_INFINITY) {
	        	        		totalSatisfyFee = (float) 5.0;
	        	        	}
	        			}
	        				
	        			//STANDARD SCORE
	        			//get standard score of school
	        			float standard  = schoolList.get(findIndex(count.getSchoolMatchingGroup(listStudentGroup[i]),listSchool)).getStandardScore();
	        			//get avgScore of group
	        			float avgScoreGroup = getAvgScore(listGroup.get(i));
	        			//count satisfy by fomula conflict/(abs(standardSore - avgScoregroup)).. It means The smaller the difference, the higher the score
	        			if(Math.abs(standard-avgScoreGroup)>1) {
	        				totalSatisfyStandardScore = totalSatisfyStandardScore + conflictData.get(1).getConflictDataSet()[0]/(Math.abs(standard-avgScoreGroup));
	        			}else {
	        				totalSatisfyStandardScore = totalSatisfyStandardScore + 10;
	        			}
	        			
	        		
	        	}
	        	
	        	//total satisfy school
	        	float satisfySchool =  (totalSatisfyNumberOfStudentTarget + totalSatisfyStandardScore)/(2*schoolList.size());
	        	//total satisfy student
	        	float satisfyStudent = (totalSatisfyPriority+totalSatisfyLocation+totalSatisfyFee)/(3*studentList.size());
	        	
	        	
	        	//Set object for GA
	        	double[] result = {satisfySchool,satisfyStudent};
	        	System.out.println("===========================================" );
	        	
	            solution.setObjectives(result);  
	        }      

	        @Override
	        public Solution newSolution() {
	            Solution solution = new Solution(studentList.size(),2);
	            for(int i=0;i<studentList.size();i++) {
	            	solution.setVariable(i, EncodingUtils.newBinaryInt(0, schoolList.size()));
	            }
			    return solution;
	    }
	    }
	 
	    public static void main(String[] args) {
	    	
			//configure and run the NSGAII function
			NondominatedPopulation result = new Executor()
					.withProblemClass(Demo.class)
					.withAlgorithm("NSGAII")
					.withMaxEvaluations(1)
					.run();
					
			//display the results
			System.out.format("SatisfySchool  SastifyStudent%n");
			
			for (Solution solution : result) {
				System.out.format("%.4f      %.4f%n",
						solution.getObjective(0),
						solution.getObjective(1));
			}
		}
}