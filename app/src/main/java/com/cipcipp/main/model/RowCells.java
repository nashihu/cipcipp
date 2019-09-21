package com.cipcipp.main.model;

import java.util.ArrayList;
import java.util.List;

public class RowCells {

    public RowCells() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getC1() {
        return c1;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }

    public String getC2() {
        return c2;
    }

    public void setC2(String c2) {
        this.c2 = c2;
    }

    public String getC3() {
        return c3;
    }

    public void setC3(String c3) {
        this.c3 = c3;
    }

    public String getC4() {
        return c4;
    }

    public void setC4(String c4) {
        this.c4 = c4;
    }

    public String getC5() {
        return c5;
    }

    public void setC5(String c5) {
        this.c5 = c5;
    }

    public String getC6() {
        return c6;
    }

    public void setC6(String c6) {
        this.c6 = c6;
    }

    public String getC7() {
        return c7;
    }

    public void setC7(String c7) {
        this.c7 = c7;
    }

    public String getC8() {
        return c8;
    }

    public void setC8(String c8) {
        this.c8 = c8;
    }

    public String getC9() {
        return c9;
    }

    public String getC26() { return c26; }

    public void setC26(String c26) { this.c26 = c26; }

    public String getC27() { return c27; }

    public void setC27(String c27) { this.c27 = c27; }

    public String getC28() { return c28; }

    public void setC28(String c28) { this.c28 = c28; }

    public String getC29() { return c29; }

    public void setC29(String c29) { this.c29 = c29; }

    public String getC30() { return c30; }

    public void setC30(String c30) { this.c30 = c30; }

    public String getC31() { return c31; }

    public void setC31(String c31) { this.c31 = c31; }

    public String getC32() { return c32; }

    public void setC32(String c32) { this.c32 = c32; }

    public void setC9(String c9) {
        this.c9 = c9;
    }

    public String getC10() {
        return c10;
    }

    public void setC10(String c10) {
        this.c10 = c10;
    }

    public String getC11() {
        return c11;
    }

    public void setC11(String c11) {
        this.c11 = c11;
    }

    public String getC12() {
        return c12;
    }

    public void setC12(String c12) {
        this.c12 = c12;
    }

    public String getC13() {
        return c13;
    }

    public void setC13(String c13) {
        this.c13 = c13;
    }

    public String getC14() {
        return c14;
    }

    public void setC14(String c14) {
        this.c14 = c14;
    }

    public String getC15() {
        return c15;
    }

    public void setC15(String c15) {
        this.c15 = c15;
    }

    public String getC16() {
        return c16;
    }

    public void setC16(String c16) {
        this.c16 = c16;
    }

    public String getC17() {
        return c17;
    }

    public void setC17(String c17) {
        this.c17 = c17;
    }

    public String getC18() {
        return c18;
    }

    public void setC18(String c18) {
        this.c18 = c18;
    }

    public String getC19() {
        return c19;
    }

    public void setC19(String c19) {
        this.c19 = c19;
    }

    public String getC20() {
        return c20;
    }

    public void setC20(String c20) {
        this.c20 = c20;
    }

    public String getC21() {
        return c21;
    }

    public void setC21(String c21) {
        this.c21 = c21;
    }

    public String getC22() {
        return c22;
    }

    public void setC22(String c22) {
        this.c22 = c22;
    }

    public String getC23() {
        return c23;
    }

    public void setC23(String c23) {
        this.c23 = c23;
    }

    public String getC24() {
        return c24;
    }

    public void setC24(String c24) {
        this.c24 = c24;
    }

    public String getC25() {
        return c25;
    }

    public void setC25(String c25) {
        this.c25 = c25;
    }

    public String logger(RowCells c) {
        return "ID: "+c.getId()
                +" ,c1: "+c.getC1()
                +" ,c2: "+c.getC2()
                +" ,c3: "+c.getC3()
                +" ,c4: "+c.getC4()
                +" ,c5: "+c.getC5()
                +" ,c6: "+c.getC6()
                +" ,c7: "+c.getC7()
                +" ,c8: "+c.getC8()
                +" ,c9: "+c.getC9()
                +" ,c10: "+c.getC10()
                +" ,c11: "+c.getC11()
                +" ,c12: "+c.getC12()
                +" ,c13: "+c.getC13()
                +" ,c14: "+c.getC14()
                +" ,c15: "+c.getC15()
                +" ,c16: "+c.getC16()
                +" ,c17: "+c.getC17()
                +" ,c18: "+c.getC18()
                +" ,c19: "+c.getC19()
                +" ,c20: "+c.getC20()
                +" ,c21: "+c.getC21()
                +" ,c22: "+c.getC22()
                +" ,c23: "+c.getC23()
                +" ,c24: "+c.getC24()
                +" ,c25: "+c.getC25()
                +" ,c26: "+c.getC26()
                +" ,c27: "+c.getC27()
                +" ,c28: "+c.getC28()
                +" ,c29: "+c.getC29()
                +" ,c30: "+c.getC30()
                +" ,c31: "+c.getC31()
                +" ,c32: "+c.getC32()
                ;
    }

    public void bulkSetter(RowCells rowCells, List<CellModel> price_) {
        String zero = "0";
        try { rowCells.setC1(price_.get(0).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC1(zero); }
        try { rowCells.setC2(price_.get(1).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC2(zero); }
        try { rowCells.setC3(price_.get(2).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC3(zero); }
        try { rowCells.setC4(price_.get(3).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC4(zero); }
        try { rowCells.setC5(price_.get(4).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC5(zero); }
        try { rowCells.setC6(price_.get(5).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC6(zero); }
        try { rowCells.setC7(price_.get(6).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC7(zero); }
        try { rowCells.setC8(price_.get(7).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC8(zero); }
        try { rowCells.setC9(price_.get(8).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC9(zero); }
        try { rowCells.setC10(price_.get(9).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC10(zero); }
        try { rowCells.setC11(price_.get(10).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC11(zero); }
        try { rowCells.setC12(price_.get(11).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC12(zero); }
        try { rowCells.setC13(price_.get(12).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC13(zero); }
        try { rowCells.setC14(price_.get(13).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC14(zero); }
        try { rowCells.setC15(price_.get(14).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC15(zero); }
        try { rowCells.setC16(price_.get(15).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC16(zero); }
        try { rowCells.setC17(price_.get(16).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC17(zero); }
        try { rowCells.setC18(price_.get(17).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC18(zero); }
        try { rowCells.setC19(price_.get(18).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC19(zero); }
        try { rowCells.setC20(price_.get(19).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC20(zero); }
        try { rowCells.setC21(price_.get(20).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC21(zero); }
        try { rowCells.setC22(price_.get(21).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC22(zero); }
        try { rowCells.setC23(price_.get(22).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC23(zero); }
        try { rowCells.setC24(price_.get(23).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC24(zero); }
        try { rowCells.setC25(price_.get(24).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC25(zero); }
        try { rowCells.setC26(price_.get(25).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC26(zero); }
        try { rowCells.setC27(price_.get(26).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC27(zero); }
        try { rowCells.setC28(price_.get(27).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC28(zero); }
        try { rowCells.setC29(price_.get(28).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC29(zero); }
        try { rowCells.setC30(price_.get(29).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC30(zero); }
        try { rowCells.setC31(price_.get(30).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC31(zero); }
        try { rowCells.setC32(price_.get(31).getData().toString()); } catch (IndexOutOfBoundsException e) { rowCells.setC32(zero); }

    }

    public ArrayList<CellModel> bulkGetter(RowCells rowCells) {
        ArrayList<CellModel> price_ = new ArrayList<>();
        if(!rowCells.getC2().equals("0") && !rowCells.getC2().toLowerCase().contains("http")){price_.add(new CellModel("1",rowCells.getC2()));}
        if(!rowCells.getC3().equals("0") && !rowCells.getC3().toLowerCase().contains("http")){price_.add(new CellModel("2",rowCells.getC3()));}
        if(!rowCells.getC4().equals("0") && !rowCells.getC4().toLowerCase().contains("http")){price_.add(new CellModel("3",rowCells.getC4()));}
        if(!rowCells.getC5().equals("0") && !rowCells.getC5().toLowerCase().contains("http")){price_.add(new CellModel("4",rowCells.getC5()));}
        if(!rowCells.getC6().equals("0") && !rowCells.getC6().toLowerCase().contains("http")){price_.add(new CellModel("5",rowCells.getC6()));}
        if(!rowCells.getC7().equals("0") && !rowCells.getC7().toLowerCase().contains("http")){price_.add(new CellModel("6",rowCells.getC7()));}
        if(!rowCells.getC8().equals("0") && !rowCells.getC8().toLowerCase().contains("http")){price_.add(new CellModel("7",rowCells.getC8()));}
        if(!rowCells.getC9().equals("0") && !rowCells.getC9().toLowerCase().contains("http")){price_.add(new CellModel("8",rowCells.getC9()));}
        if(!rowCells.getC10().equals("0") && !rowCells.getC10().toLowerCase().contains("http")){price_.add(new CellModel("9",rowCells.getC10()));}
        if(!rowCells.getC11().equals("0") && !rowCells.getC11().toLowerCase().contains("http")){price_.add(new CellModel("10",rowCells.getC11()));}
        if(!rowCells.getC12().equals("0") && !rowCells.getC12().toLowerCase().contains("http")){price_.add(new CellModel("11",rowCells.getC12()));}
        if(!rowCells.getC13().equals("0") && !rowCells.getC13().toLowerCase().contains("http")){price_.add(new CellModel("12",rowCells.getC13()));}
        if(!rowCells.getC14().equals("0") && !rowCells.getC14().toLowerCase().contains("http")){price_.add(new CellModel("13",rowCells.getC14()));}
        if(!rowCells.getC15().equals("0") && !rowCells.getC15().toLowerCase().contains("http")){price_.add(new CellModel("14",rowCells.getC15()));}
        if(!rowCells.getC16().equals("0") && !rowCells.getC16().toLowerCase().contains("http")){price_.add(new CellModel("15",rowCells.getC16()));}
        if(!rowCells.getC17().equals("0") && !rowCells.getC17().toLowerCase().contains("http")){price_.add(new CellModel("16",rowCells.getC17()));}
        if(!rowCells.getC18().equals("0") && !rowCells.getC18().toLowerCase().contains("http")){price_.add(new CellModel("17",rowCells.getC18()));}
        if(!rowCells.getC19().equals("0") && !rowCells.getC19().toLowerCase().contains("http")){price_.add(new CellModel("18",rowCells.getC19()));}
        if(!rowCells.getC20().equals("0") && !rowCells.getC20().toLowerCase().contains("http")){price_.add(new CellModel("19",rowCells.getC20()));}
        if(!rowCells.getC21().equals("0") && !rowCells.getC21().toLowerCase().contains("http")){price_.add(new CellModel("20",rowCells.getC21()));}
        if(!rowCells.getC22().equals("0") && !rowCells.getC22().toLowerCase().contains("http")){price_.add(new CellModel("21",rowCells.getC22()));}
        if(!rowCells.getC23().equals("0") && !rowCells.getC23().toLowerCase().contains("http")){price_.add(new CellModel("22",rowCells.getC23()));}
        if(!rowCells.getC24().equals("0") && !rowCells.getC24().toLowerCase().contains("http")){price_.add(new CellModel("23",rowCells.getC24()));}
        if(!rowCells.getC25().equals("0") && !rowCells.getC25().toLowerCase().contains("http")){price_.add(new CellModel("24",rowCells.getC25()));}
        if(!rowCells.getC26().equals("0") && !rowCells.getC26().toLowerCase().contains("http")){price_.add(new CellModel("25",rowCells.getC26()));}
        if(!rowCells.getC27().equals("0") && !rowCells.getC27().toLowerCase().contains("http")){price_.add(new CellModel("26",rowCells.getC27()));}
        if(!rowCells.getC28().equals("0") && !rowCells.getC28().toLowerCase().contains("http")){price_.add(new CellModel("27",rowCells.getC28()));}
        if(!rowCells.getC29().equals("0") && !rowCells.getC29().toLowerCase().contains("http")){price_.add(new CellModel("28",rowCells.getC29()));}
        if(!rowCells.getC30().equals("0") && !rowCells.getC30().toLowerCase().contains("http")){price_.add(new CellModel("29",rowCells.getC30()));}
        if(!rowCells.getC31().equals("0") && !rowCells.getC31().toLowerCase().contains("http")){price_.add(new CellModel("30",rowCells.getC31()));}
        if(!rowCells.getC32().equals("0") && !rowCells.getC32().toLowerCase().contains("http")){price_.add(new CellModel("31",rowCells.getC32()));}



        return price_;
    }

    public ArrayList<String> bulkStringGetter(RowCells rowCells) {
        ArrayList<String> colVal = new ArrayList<>();
        if(!rowCells.getC2().equals("0")) {colVal.add(rowCells.getC2());}
        if(!rowCells.getC3().equals("0")) {colVal.add(rowCells.getC3());}
        if(!rowCells.getC4().equals("0")) {colVal.add(rowCells.getC4());}
        if(!rowCells.getC5().equals("0")) {colVal.add(rowCells.getC5());}
        if(!rowCells.getC6().equals("0")) {colVal.add(rowCells.getC6());}
        if(!rowCells.getC7().equals("0")) {colVal.add(rowCells.getC7());}
        if(!rowCells.getC8().equals("0")) {colVal.add(rowCells.getC8());}
        if(!rowCells.getC9().equals("0")) {colVal.add(rowCells.getC9());}
        if(!rowCells.getC10().equals("0")) {colVal.add(rowCells.getC10());}
        if(!rowCells.getC11().equals("0")) {colVal.add(rowCells.getC11());}
        if(!rowCells.getC12().equals("0")) {colVal.add(rowCells.getC12());}
        if(!rowCells.getC13().equals("0")) {colVal.add(rowCells.getC13());}
        if(!rowCells.getC14().equals("0")) {colVal.add(rowCells.getC14());}
        if(!rowCells.getC15().equals("0")) {colVal.add(rowCells.getC15());}
        if(!rowCells.getC16().equals("0")) {colVal.add(rowCells.getC16());}
        if(!rowCells.getC17().equals("0")) {colVal.add(rowCells.getC17());}
        if(!rowCells.getC18().equals("0")) {colVal.add(rowCells.getC18());}
        if(!rowCells.getC19().equals("0")) {colVal.add(rowCells.getC19());}
        if(!rowCells.getC20().equals("0")) {colVal.add(rowCells.getC20());}
        if(!rowCells.getC21().equals("0")) {colVal.add(rowCells.getC21());}
        if(!rowCells.getC22().equals("0")) {colVal.add(rowCells.getC22());}
        if(!rowCells.getC23().equals("0")) {colVal.add(rowCells.getC23());}
        if(!rowCells.getC24().equals("0")) {colVal.add(rowCells.getC24());}
        if(!rowCells.getC25().equals("0")) {colVal.add(rowCells.getC25());}
        if(!rowCells.getC26().equals("0")) {colVal.add(rowCells.getC26());}
        if(!rowCells.getC27().equals("0")) {colVal.add(rowCells.getC27());}
        if(!rowCells.getC28().equals("0")) {colVal.add(rowCells.getC28());}
        if(!rowCells.getC29().equals("0")) {colVal.add(rowCells.getC29());}
        if(!rowCells.getC30().equals("0")) {colVal.add(rowCells.getC30());}
        if(!rowCells.getC31().equals("0")) {colVal.add(rowCells.getC31());}
        if(!rowCells.getC32().equals("0")) {colVal.add(rowCells.getC32());}
        return colVal;
    }

    private int id;
    private String c1="0";
    private String c2="0";
    private String c3="0";
    private String c4="0";
    private String c5="0";
    private String c6="0";
    private String c7="0";
    private String c8="0";
    private String c9="0";
    private String c10="0";
    private String c11="0";
    private String c12="0";
    private String c13="0";
    private String c14="0";
    private String c15="0";
    private String c16="0";
    private String c17="0";
    private String c18="0";
    private String c19="0";
    private String c20="0";
    private String c21="0";
    private String c22="0";
    private String c23="0";
    private String c24="0";
    private String c25="0";
    private String c26="0";
    private String c27="0";
    private String c28="0";
    private String c29="0";
    private String c30="0";
    private String c31="0";
    private String c32="0";

}
