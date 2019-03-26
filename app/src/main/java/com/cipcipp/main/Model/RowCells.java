package com.cipcipp.main.Model;

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
                +" ,c20: "+c.getC20();
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
}
