import java.text.DecimalFormat;
import java.util.*;

public class User {

    public static void main(String[] args){

        Scanner input = new Scanner(System.in);
        int Numpatients = input.nextInt();
        Patient p;
        for (int i = 0; i < Numpatients; i++) {
            String _name = input.next();
            float _temp = input.nextFloat();
            int _oxygen = input.nextInt();
            int _age = input.nextInt();
            p = new Patient(_name, _age, _oxygen, _temp);
        }

        Health_Coordinator HC=new Health_Coordinator();

        while(true){
            int q = input.nextInt();
            if (q == 1) {
                String a = input.next();
                System.out.print("Temperature Criteria - ");
                int t = input.nextInt();
                System.out.print("Oxygen Levels - ");
                int o = input.nextInt();
                System.out.print("Number of Available beds - ");
                int av = input.nextInt();
                Health_Institute health = new Health_Institute(a, o, t, av);

                int numeligible=HC.AddHealthCareIns(health,Numpatients);

                int counter = 0;
                if (numeligible >= av) {
                    for (int i = 0; i < Numpatients; i++) {

                        if (counter < av) {
                            if (Patient.patient_arr.get(i).getOxygen() >= o && Patient.patient_arr.get(i).getAdmitted() == false) {
                                System.out.print("Recovery days for admitted patient ID " + (i + 1) + " - ");
                                int rec = input.nextInt();
                                Patient.patient_arr.get(i).setRecov(rec);
                                Patient.patient_arr.get(i).setAdmitted(true);
                                health.arrp.add(Patient.patient_arr.get(i));
                                Patient.patient_arr.get(i).setHealthI(health.getName());
                                counter += 1;
                            }
                        } else {
                            break;
                        }
                    }
                    for (int i = 0; i < Numpatients; i++) {
                        if (counter < av) {
                            if (Patient.patient_arr.get(i).getTemp() <= t && Patient.patient_arr.get(i).getAdmitted() == false) {
                                System.out.print("Recovery days for admitted patient ID " + (i + 1) + " - ");
                                int rec = input.nextInt();
                                Patient.patient_arr.get(i).setRecov(rec);
                                Patient.patient_arr.get(i).setAdmitted(true);
                                health.arrp.add(Patient.patient_arr.get(i));
                                counter += 1;
                            }
                        } else {
                            break;
                        }
                    }
                    health.setAvbeds(av - counter);
                    health.setAdmission(false);
                } else {
                    for (int i = 0; i < Numpatients; i++) {
                        if (Patient.patient_arr.get(i).getOxygen() >= o && Patient.patient_arr.get(i).getAdmitted() == false) {
                            System.out.print("Recovery days for admitted patient ID " + (i + 1) + " - ");
                            int rec = input.nextInt();
                            Patient.patient_arr.get(i).setRecov(rec);
                            Patient.patient_arr.get(i).setAdmitted(true);
                            health.arrp.add(Patient.patient_arr.get(i));
                        }
                    }
                    for (int i = 0; i < Numpatients; i++) {
                        if (Patient.patient_arr.get(i).getTemp() <= t && Patient.patient_arr.get(i).getAdmitted() == false) {
                            System.out.print("Recovery days for admitted patient ID " + (i + 1) + " - ");
                            int rec = input.nextInt();
                            Patient.patient_arr.get(i).setRecov(rec);
                            Patient.patient_arr.get(i).setAdmitted(true);
                            health.arrp.add(Patient.patient_arr.get(i));
                        }
                    }
                    health.setAvbeds(av-numeligible);
                }
            }
            if (q == 2) {

                HC.DeletePatients(Numpatients);
            }
            if (q == 3) {
                HC.DeleteHospital();
            }
            if (q == 4) {
                HC.numPatientsInCamp(Numpatients);
            }
            if (q == 5) {
                HC.numberofHealthCare();
            }
            if (q == 6) {
                String a = input.next();
                HC.HealthInstitueDeets(a);
            }
            if (q == 7) {
                int id = input.nextInt();
                HC.PatientDeets(id,Numpatients);
            }
            if (q == 8) {
                HC.allPatients(Numpatients);
            }
            if (q == 9) {
                String a = input.next();
                HC.PatientsInHospitalA(a);
            }
            if (q == 10) {
                break;
            }
        }

    }
}

class Health_Institute {
    private final String name;
    private final int minoxygen;
    private final float maxtemp;
    private static int rego_counterh;
    private int rego_idh;
    public static ArrayList<Health_Institute> HealthIns=new ArrayList<Health_Institute>();
    private boolean admission;
    private int Avbeds;
    public ArrayList<Patient> arrp=new ArrayList<Patient>();
    {
        rego_idh=++rego_counterh;
    }

    public Health_Institute(String _name,int _minoxygen,float _maxtemp,int _Avbeds){
        this.name=_name;
        this.minoxygen=_minoxygen;
        this.maxtemp=_maxtemp;
        this.Avbeds=_Avbeds;
        this.admission=true;
        HealthIns.add(this);
    }
    public String getName(){
        return name;
    }

    public int getMinoxygen(){
        return minoxygen;
    }

    public float getMaxtemp(){
        return maxtemp;
    }

    public int getRego_idh(){
        return rego_idh;
    }

    public boolean getAdmission(){
        return admission;
    }

    public int getAvbeds(){
        return Avbeds;
    }

    public void setAvbeds(int a){
        this.Avbeds=a;
    }

    public void setAdmission(boolean _admission){
        this.admission=_admission;
    }

}

class Patient {

    private final String name;
    private int age=0; // age
    private int oxygen=0;
    private final double temp;
    private static int rego_counterp;
    private int rego_idp;
    public static ArrayList<Patient> patient_arr=new ArrayList<Patient>();
    public static int index=0;
    private int recovDt;
    private boolean admitted=false;
    private String HealthI;
    private boolean deleted=false;
    private boolean InsDeleted=false;

    {
        rego_idp=++rego_counterp;
    }

    public Patient(String name,int _age,int _oxygen,double _temp){
        this.name=name;
        this.age=_age;
        this.oxygen=_oxygen;
        this.temp=_temp;
        this.deleted=false;
        this.InsDeleted=false;
        patient_arr.add(this);
    }

    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
    public int getOxygen(){
        return oxygen;
    }
    public double getTemp(){
        return temp;
    }
    public int getRego_idp(){
        return rego_idp;
    }

    public int getRecovDt(){
        return recovDt;
    }

    public void setRecov(int _setRecov){
        this.recovDt=_setRecov;
    }

    public void setAdmitted(boolean _admitted){
        this.admitted=_admitted;
    }

    public boolean getAdmitted(){
        return admitted;
    }

    public String getHealthI(){
        return HealthI;
    }
    public void setHealthI(String _s){
        this.HealthI=_s;
    }

    public boolean getDeleted(){
        return deleted;
    }
    public void setDeleted(boolean del){
        this.deleted=del;
    }

    public void setInsDeleted(boolean deleted){
        this.InsDeleted=deleted;
    }

    public boolean getInsDeleted(){
        return InsDeleted;
    }

}

class Health_Coordinator{

    public Health_Coordinator(){
    }

    int AddHealthCareIns(Health_Institute health,int Numpatients){
        String a=health.getName();
        float t=health.getMaxtemp();
        int o=health.getMinoxygen();
        int av=health.getAvbeds();
        System.out.println(a);
        System.out.print("Temperature should be <= ");
        System.out.println(t);
        System.out.print("Oxygen levels should be >= ");
        System.out.println(o);
        System.out.print("Number of Available beds - ");
        System.out.println(av);
        System.out.print("Admission Status - ");
        if (health.getAdmission()) {
            System.out.println("OPEN");
        } else {
            System.out.println("CLOSE");
        }
        int numeligible = 0;
        for (int i = 0; i < Numpatients; i++) {
            if ((Patient.patient_arr.get(i).getOxygen() >= o || Patient.patient_arr.get(i).getTemp() <= t) && Patient.patient_arr.get(i).getAdmitted() == false) {
                numeligible += 1;
            }
        }
        return numeligible;
    }

    void numberofHealthCare(){

        int cnt = 0;
        for (int i = 0; i < Health_Institute.HealthIns.size(); i++) {
            if (Health_Institute.HealthIns.get(i).getAdmission()) {
                cnt += 1;
            }
        }
        System.out.println(cnt + " institutes are admitting patients currently");
    }

    void allPatients(int Numpatients){
        for (int i = 0; i < Numpatients; i++) {
            if (!Patient.patient_arr.get(i).getDeleted()) {
                System.out.print((i + 1) + " ");
                System.out.println(Patient.patient_arr.get(i).getName());
            }
        }
    }

    void numPatientsInCamp(int Numpatients){
        int cnt1 = 0;
        for (int i = 0; i < Numpatients; i++) {
            if (!Patient.patient_arr.get(i).getAdmitted()) {
                cnt1 += 1;
            }
        }
        System.out.println(cnt1 + " patients");
    }

    void HealthInstitueDeets(String a){
        for (int i = 0; i < Health_Institute.HealthIns.size(); i++) {
            if (Health_Institute.HealthIns.get(i).getName().equals(a)) {
                System.out.println(Health_Institute.HealthIns.get(i).getName());
                System.out.print("Temperature should be <= ");
                System.out.println(Health_Institute.HealthIns.get(i).getMaxtemp());
                System.out.print("Oxygen levels should be >= ");
                System.out.println(Health_Institute.HealthIns.get(i).getMinoxygen());
                System.out.print("Number of Available beds - ");
                System.out.println(Health_Institute.HealthIns.get(i).getAvbeds());
                System.out.print("Admission Status - ");
                if (Health_Institute.HealthIns.get(i).getAdmission()) {
                    System.out.println("OPEN");
                } else {
                    System.out.println("ClOSED");
                }
            }
        }
    }

    void PatientDeets(int id,int Numpatients){
        for (int i = 0; i < Numpatients; i++) {
            if (Patient.patient_arr.get(i).getRego_idp() == id) {
                System.out.println(Patient.patient_arr.get(i).getName());
                DecimalFormat numberFormat = new DecimalFormat("#.0");
                System.out.println("Temperature is " + numberFormat.format(Patient.patient_arr.get(i).getTemp()));
                System.out.println("Oxygen levels is " + Patient.patient_arr.get(i).getOxygen());
                if(Patient.patient_arr.get(i).getInsDeleted()){
                    System.out.print(Patient.patient_arr.get(i).getHealthI());
                    System.out.println(" is deleted");
                }
                else {
                    if (Patient.patient_arr.get(i).getAdmitted()) {
                        System.out.println("Admission Status – Admitted");
                        System.out.println("Admitting Institute - " + Patient.patient_arr.get(i).getHealthI());
                    } else {
                        System.out.println("Admission Status – Not Admitted Yet");
                    }
                }
            }
        }
    }

    void PatientsInHospitalA(String a){
        for (int i = 0; i < Health_Institute.HealthIns.size(); i++) {
            if (Health_Institute.HealthIns.get(i).getName().equals(a)) {
                for (int j = 0; j < Health_Institute.HealthIns.get(i).arrp.size(); j++) {
                    String b1 = Health_Institute.HealthIns.get(i).arrp.get(j).getName();
                    int b2 = Health_Institute.HealthIns.get(i).arrp.get(j).getRecovDt();
                    System.out.print(b1 + ", recovery time is ");
                    System.out.println(b2 + " days");

                }
            }

        }
    }

    void DeletePatients(int Numpatients){

        boolean flag1 = false;
        for (int i = 0; i < Numpatients; i++) {
            if (Patient.patient_arr.get(i).getAdmitted()) {
                if (!flag1) {
                    System.out.println("Account ID removed of admitted patients");
                }
                flag1 = true;
                System.out.println(Patient.patient_arr.get(i).getRego_idp());
                Patient.patient_arr.get(i).setDeleted(true);

            }
        }
        if (!flag1) {
            System.out.println("No patient is admitted");
        }
    }

    void DeleteHospital(){
        boolean flag = false;
        for (int i = 0; i < Health_Institute.HealthIns.size(); i++) {
            if (!Health_Institute.HealthIns.get(i).getAdmission()) {
                if (!flag) {
                    System.out.println("Accounts removed of Institute whose admission is closed");
                }
                flag = true;
                System.out.println(Health_Institute.HealthIns.get(i).getName());
//                Health_Institute.HealthIns.get(i).setAdmission(false);
                for(int j=0;j<Health_Institute.HealthIns.get(i).arrp.size();j++){
                    Health_Institute.HealthIns.get(i).arrp.get(j).setInsDeleted(true);
                }
            }
        }
        if (!flag) {
            System.out.println("No account who hasn't closed admission");
        }
    }
}
