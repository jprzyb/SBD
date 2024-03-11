package sql;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IntoFile {

    public IntoFile(String path) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(path);
        List<List<String>> data = new ArrayList<>();
        data.add(insertApartament());//0            180
        data.add(insertCars());//1                  100
        data.add(insertDates());//2                 180*2
        data.add(insertKeys());//3                  180*3
        data.add(insertPilot());//4                 180*2
        data.add(insertPerson());//5                180*2.5
        data.add(insertWorkers());//6               20
        data.add(insertParking_Space_Cars());//7    180*1.5
        data.add(insertParking_space());//8         180*2
        data.add(insertRent());//9                  180*5
        //for(String a : data.get(9)) System.out.println(a);

        for(int i=0;i<data.size();i++){

            switch (i){
                case 0: printWriter.println("-- Insert into table: Apartament data"); break;
                case 1: printWriter.println("-- Insert into table: Cars data"); break;
                case 2: printWriter.println("-- Insert into table: Dates data"); break;
                case 3: printWriter.println("-- Insert into table: Keys data"); break;
                case 4: printWriter.println("-- Insert into table: Pilot data"); break;
                case 5: printWriter.println("-- Insert into table: Person data"); break;
                case 6: printWriter.println("-- Insert into table: Workers data"); break;
                case 7: printWriter.println("-- Insert into table: Parking_Space_Cars data"); break;
                case 8: printWriter.println("-- Insert into table: Parking_space data"); break;
                case 9: printWriter.println("-- Insert into table: Rent data"); break;
            }

            for(int j=0;j<data.get(i).size();j++){
                printWriter.println(data.get(i).get(j));
            }
            printWriter.println();
        }
        printWriter.close();
    }

    public List<String> insertApartament(){
        // 4 flores per staircase * 3 apartaments per staircase * 5 blocks * 3 staircases=
        List<String> list = new ArrayList<>();
        for(int i=0;i<180;i++){
            list.add("INSERT INTO Apartament(ID, Block, Stairacase, Floor) VALUES (" +
                    i+", "+getRandomInt(1,5)+", "+getRandomInt(1,3)+", "+getRandomInt(1,4)+");");
        }
        return list;
    }
    public List<String> insertCars(){
        List<String> list = new ArrayList<>();
        for(int i=0;i<100;i++){
            list.add("INSERT INTO Cars(ID) VALUES (" + i + ");");
        }
        return list;
    }
    public List<String> insertDates(){
        List<String> list = new ArrayList<>();
        for(int i=0;i<180*2;i++){
            list.add("INSERT INTO Dates(ID , Dates) VALUES (" + i + ", '"+ getRandomInt( 1950, 2023)+"-"+getRandomInt(1,12)+"-"+getRandomInt(1 ,28) + "');");
        }
        return list;
    }
    public List<String> insertKeys(){
        //3 pairs per apartament (2 for locators, 1 spare)
        List<String> list = new ArrayList<>();
        int apId=0;
        for(int i=0;i<(180*3);i++){
            if(i%3==0 && i!=0) apId++;
            list.add("INSERT INTO keys(ID , Apartament_ID) VALUES (" + i + ", "+apId +");");
        }
        return list;
    }
    public List<String> insertPilot(){
        //1 for apartament, 1 spare
        List<String> list = new ArrayList<>();
        int apId=0;
        for(int i=0;i<(180*2);i++){
            if(i%2==0 && i!=0) apId++;
            list.add("INSERT INTO Pilot(ID , Apartament_ID) VALUES (" + i + ", "+apId +");");
        }
        return list;
    }
    public List<String> insertPerson(){
        List<String> list = new ArrayList<>();

        int gender=0;
        for(int i=0;i<(int)(180*2.5);i++){
            gender = getRandomInt(0,999); // 0->Male , 1->Female
            if(gender%2==0) {
                list.add("INSERT INTO Person(ID ,Name, Surename, Apartament_ID) VALUES (" + i + ", '"+getNamesM(getRandomInt(0,20))+"', '"+getSurenamesM(getRandomInt(0,20))+"', "+ getRandomInt(0,179) +");");
            }
            else {
                list.add("INSERT INTO Person(ID ,Name, Surename, Apartament_ID) VALUES (" + i + ", '"+getNamesF(getRandomInt(0,20))+"', '"+getSurenamesF(getRandomInt(0,20))+"', "+ getRandomInt(0,179) +");");
            }

        }
        return list;
    }
    public List<String> insertWorkers(){
        List<String> list = new ArrayList<>();
        List<Integer> personId = new ArrayList<>();

        for(int i=0;i<20;i++){
            int id = getRandomInt(0, 449);
            if(!personId.contains(id)) personId.add(id);
            else i--;
        }

        int salary;
        int bossId;
        String role;
        for(int i=0;i<20;i++){
            if(i<4)  salary = getRandomInt(2750 , 4000);
            else  salary = getRandomInt(4500 , 6000);
            if(i==0) role = getRole(0);
            else if(i==1) role = getRole(1);
            else if(i==2) role = getRole(2);
            else if(i==3) role = getRole(3);
            else role = getRole(getRandomInt(0,3));

            if(i<4) bossId=-1;
            else if(role==getRole(0) && i>=4) bossId=0;
            else if(role==getRole(1) && i>=4) bossId=1;
            else if(role==getRole(2) && i>=4) bossId=2;
            else if(role==getRole(3) && i>=4) bossId=3;
            else bossId = getRandomInt(0,3);

            String boss = String.valueOf(bossId==(-1) ? "NULL" : bossId);


            list.add( "INSERT INTO Workers(ID, Role, Salary, WorkBegin, BOSS_ID) VALUES ("+ i +", '"+role+"', "+salary+ ", "+getRandomInt(0,(180*2)) +", "+ boss + ");" );
        }
        return list;
    }
    public List<String> insertParking_Space_Cars(){
        List<String> list = new ArrayList<>();

        for(int i=0;i<(int)(180*1.5);i++){
                list.add("INSERT INTO Parking_Space_Cars(Parking_Space_ID, Cars_Car_ID) VALUES ("+  getRandomInt(0,(int)(180*1.5))+", "+getRandomInt(0,100)+ ");");
        }
        return list;
    }
    public List<String> insertParking_space(){
        List<String> list = new ArrayList<>();

        for(int i=0;i<(int)(180*2);i++){
            if(i%4!=0) list.add("INSERT INTO Parking_space(ID, Apartament_ID) VALUES ("+i+", "+getRandomInt(0,179)+");");
            else list.add("INSERT INTO Parking_space(ID, Apartament_ID) VALUES ("+i+", NULL);");
        }
        return list;
    }
    public List<String> insertRent(){
        List<String> list = new ArrayList<>();

        for(int i=0;i<(int)(180*5);i++){
            list.add("INSERT INTO Rent(ID, Amount, Dates_ID, Apartament_ID) VALUES ("+i+", "+getRandomInt(700,5000)+", "+getRandomInt(0,180*2)+", "+getRandomInt(0,179)+");");
        }
        return list;
    }

    public int getRandomInt(int min , int max){
        Random r = new Random();
        return (int)(r.nextInt(min,max+1));
    }
    public String getSurenamesM(int n){

        switch(n){
            case 0: return "Kowalski";
            case 1: return "Nowak";
            case 2: return "Malina";
            case 3: return "Baczel";
            case 4: return "Bai";
            case 5: return "Piotrowski";
            case 6: return "Jurkowski";
            case 7: return "Cwintal";
            case 8: return "Bazodanowy";
            case 9: return "Techniczny";
            case 10: return "Morski";
            case 11: return "Andrzejczuk";
            case 12: return "Leszczuk";
            case 13: return "Ampersand";
            case 14: return "Blotny";
            case 15: return "Kowalewski";
            case 16: return "Mazur";
            case 17: return "Zuziewicz";
            case 18: return "Dynda";
            case 19: return "Rozpracowany";
            default: return "Piatkowski";
        }
    }
    public String getSurenamesF(int n){

        switch(n){
            case 0: return "Kowalska";
            case 1: return "Nowak";
            case 2: return "Malina";
            case 3: return "Baczel";
            case 4: return "Bai";
            case 5: return "Piotrowska";
            case 6: return "Jurkowska";
            case 7: return "Cwintal";
            case 8: return "Bazodanowa";
            case 9: return "Techniczna";
            case 10: return "Morska";
            case 11: return "Andrzejczuk";
            case 12: return "Leszczuk";
            case 13: return "Ampersand";
            case 14: return "Blotna";
            case 15: return "Kowalewska";
            case 16: return "Mazur";
            case 17: return "Zuziewicz";
            case 18: return "Dynda";
            case 19: return "Rozpracowana";
            default: return "Piatkowska";
        }
    }
    public String getNamesF(int n){

        switch(n){
            case 0: return "Daria";
            case 1: return "Ania";
            case 2: return "Anna";
            case 3: return "Kamila";
            case 4: return "Klaudia";
            case 5: return "Ewa";
            case 6: return "Maja";
            case 7: return "Anita";
            case 8: return "Iwona";
            case 9: return "Renata";
            case 10: return "Zuzanna";
            case 11: return "Joanna";
            case 12: return "Alina";
            case 13: return "Aleksandra";
            case 14: return "Wiktoria";
            case 15: return "Weronika";
            case 16: return "Ariana";
            case 17: return "Dzesika";
            case 18: return "Marianna";
            case 19: return "Nadia";
            default: return "Marysia";
        }
    }
    public String getNamesM(int n){

        switch(n){
            case 0: return "Jakub";
            case 1: return "Andrzej";
            case 2: return "Robert";
            case 3: return "Kacper";
            case 4: return "Lukasz";
            case 5: return "Wojtek";
            case 6: return "Krzysztof";
            case 7: return "Tomasz";
            case 8: return "Marian";
            case 9: return "Jan";
            case 10: return "Janusz";
            case 11: return "Arkadiusz";
            case 12: return "Marek";
            case 13: return "Sergii";
            case 14: return "Lorenz";
            case 15: return "Hipolit";
            case 16: return "Walenty";
            case 17: return "Filip";
            case 18: return "Hubert";
            case 19: return "Konstantyn";
            default: return "Stefan";
        }
    }
    public String getRole(int n){
        switch (n){
            case 0: return "Sprzatacz";
            case 1: return "Konserwator";
            case 2: return "Ochrona";
            case 3: return "Ogrodnik";
            default: return "NULL";
        }
    }

} // EOF
