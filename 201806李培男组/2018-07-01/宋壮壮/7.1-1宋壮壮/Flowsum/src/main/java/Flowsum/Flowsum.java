package Flowsum;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class Flowsum implements WritableComparable<Flowsum>{


    private String phoneNB;
    private long u_load;
    private long d_load;
    private long s_load;


    public Flowsum(){

    }

    public Flowsum(String phoneNB, long u_load, long d_load) {
        super();
        this.phoneNB = phoneNB;
        this.u_load = u_load;
        this.d_load = d_load;
        this.s_load=u_load+d_load;
    }

    public String getPhoneNB() {
        return phoneNB;
    }

    public void setPhoneNB(String phoneNB) {
        this.phoneNB = phoneNB;
    }

    public long getU_load() {
        return u_load;
    }

    public void setU_load(long u_load) {
        this.u_load = u_load;
    }

    public long getD_load() {
        return d_load;
    }

    public void setD_load(long d_load) {
        this.d_load = d_load;
    }

    public long getS_load() {
        return s_load;
    }

    public void setS_load(long s_load) {
        this.s_load = s_load;
    }


    public void write(DataOutput out) throws IOException {

        out.writeUTF(phoneNB);
        out.writeLong(d_load);
        out.writeLong(u_load);
        out.writeLong(s_load);

    }

    public void readFields(DataInput in) throws IOException {


        phoneNB=in.readUTF();
        d_load=in.readLong();
        u_load=in.readLong();
        s_load=in.readLong();

    }

    public String toString() {
        return ""+d_load+"\t"+u_load+"\t"+s_load;
    }

    public int compareTo(Flowsum arg0) {
        // TODO Auto-generated method stub
        return s_load>arg0.getS_load()?-1:1;
    }




}
