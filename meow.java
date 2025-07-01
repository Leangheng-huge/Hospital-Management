import java.util.Scanner;
public class meow {
    public static void main(String[] arg){
        Scanner s = new Scanner(System.in);

        int[] Patient_Id = new int[25]; // Patient_Id[12,23]
        String[] Patient_Name = new String[60];
        int[] Patient_Age = new int[25];
        String[] Patient_Disease = new String[25];
        int PatientCount = 0;
        int option = 0;
        int age = 0;
        boolean[] available_room = new boolean[10];// 101-110
        int id;
        int verifyId;
        String disease = "";
        int room ;
        boolean patientFound = false;
        char adding = '0';
        int patientIndex ;

        do{
            System.out.println("=====Hospital System====");
            System.out.println("1 -> : Register Patient ");
            System.out.println("2 -> : Assign Doctor ");
            System.out.println("3 -> : Check-In Patient to Room ");
            System.out.println("4 -> : Bill for Patient ");
            System.out.println("5 -> : Discharge Patient ");
            System.out.println("6 -> : Patient's Information ");
            System.out.println("7 -> : Exit the System ");
            System.out.print("Input option : ");
            option = s.nextInt();
            s.nextLine();

            switch (option){
                case 1 -> {
                    // Register Patient
                    age = 0;
                    String name;
                    do {

                        do {
                            System.out.println("Please enter the patient's id "); //id 12,23
                            id = s.nextInt();
                            s.nextLine();

                            do {
                                System.out.println("Please enter the patient's name :");
                                name = s.nextLine().trim();
                                if (name.isEmpty()) {
                                    System.out.println("****Name cannot be empty. Please enter patient's name again.****");
                                }
                            } while (name.isEmpty());

                            System.out.println("Please Enter Age : ");
                            age = s.nextInt();
                            s.nextLine();

                            System.out.println("Please enter the patient disease : ");
                            disease = s.nextLine();
                        } while (age < 0);
                        if (age > 0) {
                            Patient_Id[PatientCount] = id;
                            Patient_Name[PatientCount] = name;
                            Patient_Age[PatientCount] = age;
                            Patient_Disease[PatientCount] = disease;
                            PatientCount++; // it will to index 1

                            System.out.println("\nPatient registered successfully!");

                            System.out.println("Patient #" + (PatientCount));


                            System.out.println("\nDo you want to add another patient? (y/n):");
                            adding = s.next().toLowerCase().charAt(0);
                            s.nextLine();
                            if (adding == 'y') {
                                continue;
                            } else {
                                System.out.println("\nExiting system...");
                                break;
                            }
                        }
                    }while(adding=='y');
                    System.out.println("Total Patient register :" + PatientCount);
                    break;
                }
                case 2->{
                    // Assign Doctor
                    System.out.println("Please enter the patient's id : ");
                    id = s.nextInt();s.nextLine();
                    System.out.println("Verify the patient's id : ");
                    verifyId = s.nextInt();s.nextLine();
                    // Check if patient exist
                    if(id == verifyId) {
                        patientFound = false;
                        patientIndex = -1;

                        for (int i = 0; i < PatientCount; i++) {
                            if (Patient_Id[i] == id) {
                                patientFound = true;
                                patientIndex = i;
                                // Enter dr specialization
                                System.out.println("Patient exist in the system");
                                System.out.println("ID: " + Patient_Id[patientIndex]);

                                System.out.println("Patient disease: " + Patient_Disease[patientIndex]);
                                System.out.println("\nEnter Doctor Specialized ");
                                System.out.println("1.General Medicine");
                                System.out.println("2.Cardiology");
                                System.out.println("3.Neurology");
                                System.out.println("4.Pulmonology");
                                System.out.println("5.Oncology");
                                System.out.println("Enter the option : ");
                                int Dr_option= s.nextByte();
                                if(Dr_option>=1 && Dr_option<=5){
                                    System.out.println("Doctor Specialized: " + Dr_option);
                                }else{
                                    System.out.println("Invalid Option");
                                }
                                boolean[] Dr_available = new boolean[5];
                                for (int j = 0; j < Dr_available.length; j++) {
                                    Dr_available[j] = true;
                                }
                                System.out.println("\n--- Current Doctor Status ---");
                                for (int j = 0; j < Dr_available.length; j++) {
                                    int doctorNumber = j + 1;
                                    String status = Dr_available[j] ? "Available" : "Occupied";
                                    System.out.println("Doctor " + doctorNumber + ": " + status);
                                }while(true){
                                    System.out.println("\nEnter doctor number to assign (1-5) or 0 to exit: ");
                                    int doctor = s.nextInt();s.nextLine();
                                    if(doctor==0){
                                        System.out.println("Exiting system...");
                                        break;
                                    }
                                    if(doctor<1 || doctor>5){
                                        System.out.println("Invalid doctor number! Please enter a doctor number between 1-5.");
                                        continue;
                                    }
                                    int index = doctor - 1; // Convert doctor number to array index
                                    if(Dr_available[index]){
                                        // Doctor is available
                                        Dr_available[index] = false; // Mark as occupied
                                        System.out.println(" Doctor successfully assigned to patient " + id);
                                    }else{
                                        // Doctor is already occupied
                                        System.out.println("Doctor " + doctor + " is no longer available. It's already occupied.");
                                    }
                                }
                            } else if (i == PatientCount - 1) {
                                System.out.println("Patient doesn't exist in the system");
                            }
                        }
                    }
                }
                case 3-> {
                    // Check-In Patient to Room

                    System.out.println("Enter patient ID: ");
                    id = s.nextInt();s.nextLine();

                    patientFound = false;
                   for (int i = 0; i < PatientCount; i++) {
                       if (Patient_Id[i] == id) {
                           patientFound = true;
                           System.out.println("Patient exists in the system");
                           if (PatientCount == 1){
                               boolean[] availableRoom = new boolean[10]; // 101 to 110
                               for(int k=0;k<availableRoom.length;k++){
                                   availableRoom[k]=true;
                           }
                               System.out.println("\nAvailable rooms: 101-110");
                               System.out.println("All 10 rooms (101-110) are initially available.");

                               System.out.println("\n--- Current Room Status ---");
                               for (int j = 0; j < availableRoom.length; j++) {
                                   int roomNumber = j + 101;     // 0+101=101 ->  index 0
                                   String status = availableRoom[j] ? "Available" : "Occupied";
                                   System.out.println("Room " + roomNumber + ": " + status);
                               }while(true){
                                   System.out.println("\nEnter room number to check-in (101-110) or 0 to exit: ");
                                   room = s.nextInt();s.nextLine();

                                   if(room==0){
                                       System.out.println("Exiting system...");
                                       break;
                                   }
                                   if(room<101 || room>110){
                                       System.out.println("Invalid room number! Please enter a room number between 101-110.");
                                       continue;
                                   }
                                   int index = room - 101; // Convert room number to array index
                                   //  Ex: we have boolean[10] n -1 = 101-101=0 til 110-109=9
                                   // So 0-9 = [10]
                                   if(availableRoom[index]){
                                       // Room is available
                                       availableRoom[index] = false; // Mark as occupied
                                       System.out.println(" Patient successfully checked into room " + room);

                                   }else{
                                       // Room is already occupied
                                       System.out.println("Room " + room + " is no longer available. It's already occupied.");
                                   }
                                   // Display current room status after each check-in attempt
                                   System.out.println("\n--- Current Room Status ---");
                                   for (int j = 0; j < availableRoom.length; j++) {
                                       int roomNumber = j + 101;
                                       String status = availableRoom[j] ? "Available" : "Occupied";
                                       System.out.println("Room " + roomNumber + ": " + status);
                                   }
                               }
                           }
                       }
                   }if (!patientFound){
                       System.out.println("Patient doesn't exist in the system");
                    }
                }
                case 4 -> {
                    // Bill for Patient

                    System.out.println("Please enter the patient's id : ");
                    id = s.nextInt(); s.nextLine();

                    patientFound = false;
                    for (int i = 0; i < PatientCount; i++) {
                        if (Patient_Id[i] == id) {
                            patientFound = true;
                            System.out.println("Patient exists in the system");


                        }if (!patientFound){
                            System.out.println("Patient doesn't exist in the system");
                        }
                    }
                }
                    case 5 -> {
                        // 1. set the dr back to available when they discharge
                        // 2. set the room back to available
                        // 3. set the patient's status to discharged
                        System.out.print("Please Enter Patient ID : ");
                        id = s.nextInt();
                        String answer = "";
                        boolean found = false;

                        for (int i = 0; i < PatientCount; i++) {
                            if (Patient_Id[i] == id) {
                                System.out.println("=====Discharge Patient=====\n");

                                System.out.println("Patient " + (i + 1) + " found ");
                                System.out.println("Patient ID : " + Patient_Id[i]);
                                System.out.println("Patient Name :" + Patient_Name[i]);
                                System.out.println("Patient Age :" + Patient_Age[i]);
                                System.out.println("Disease :" + Patient_Disease[i]);
                                found = true;
                            }
                        }
                        if (!found) {
                            System.out.println("\nPatient not found!\n");
                        }
                        // set the dr back to available
                        if (found) {
                            for (int j = 0; j < available_room.length; j++) {
                                available_room[j] = true;
                            }
                            System.out.println("Patient successfully discharged");
                        }
                        // set the room back to available


                    }

                case 6 -> {
                    if(age == 0){
                        System.out.println("Please try again! ");
                    }else {
                        System.out.println("====Patient's Information");
                        System.out.println("Total Patient Details :" + PatientCount);
                    }
                    for (int i = 0; i < PatientCount; i++) {
                        System.out.println("Patient " + (i + 1));
                        System.out.println("Patient's Id " +Patient_Id[i]);
                        System.out.println("Patient's Name " +Patient_Name[i]);
                        System.out.println("Patient's Age " +Patient_Age[i]);
                        System.out.println("Patient's Disease " +Patient_Disease[i]);
                    }
                }
            }
        }while(option!=7);
    }
}