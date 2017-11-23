package com.lgardias.model;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JDialogFis;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;

public class FISModel {
    private FIS fis;
    private JDialogFis dialogFis;

    public FISModel() {
        fis = FIS.load(FISModel.class.getClassLoader().getResourceAsStream("fcl_file/submarine.fcl"), true);
    }

    public void createDialogFis() {
        dialogFis = new JDialogFis(fis);
    }

    public void setDistance(double value) {

        fis.setVariable("IN_distance", value);
    }

    public void setSpeed(double value) {

        fis.setVariable("IN_falling_speed", value);
    }

    public String distanceMembership() {
        return new String("\nPrzynależność \"GŁĘBOKOŚCI ZANURZENIA\" do zbiorów rozmytych \n" +
                "level_0 : " + fis.getVariable("IN_distance").getMembership("level_0") + "\n" +
                "level_1 : " + fis.getVariable("IN_distance").getMembership("level_1") + "\n" +
                "level_2 : " + fis.getVariable("IN_distance").getMembership("level_2") + "\n" +
                "level_3 : " + fis.getVariable("IN_distance").getMembership("level_3") + "\n" +
                "level_4 : " + fis.getVariable("IN_distance").getMembership("level_4") + "\n" +
                "level_5 : " + fis.getVariable("IN_distance").getMembership("level_5") + "\n" +
                "level_6 : " + fis.getVariable("IN_distance").getMembership("level_6") + "\n" +
                "level_7 : " + fis.getVariable("IN_distance").getMembership("level_7") + "\n\n"
        );
    }

    public String speedMembership() {
        return new String("Przynależność \"SZYBKOŚCI ZANURZENIA\" do zbiorów rozmytych \n" +
                "gear_1 : " + fis.getVariable("IN_falling_speed").getMembership("gear_1") + "\n" +
                "gear_2 : " + fis.getVariable("IN_falling_speed").getMembership("gear_2") + "\n" +
                "gear_3 : " + fis.getVariable("IN_falling_speed").getMembership("gear_3") + "\n" +
                "gear_4 : " + fis.getVariable("IN_falling_speed").getMembership("gear_4") + "\n" +
                "gear_5 : " + fis.getVariable("IN_falling_speed").getMembership("gear_5") + "\n\n"
        );
    }

    public String balastMembership() {
        return new String("Przynależność \"WYPEŁNIENIA BALASTU\" do zbiorów rozmytych \n" +
                "val_0 : " + fis.getVariable("OUT_ballast_dropping").getMembership("val_0") + "\n" +
                "val_20 : " + fis.getVariable("OUT_ballast_dropping").getMembership("val_20") + "\n" +
                "val_40 : " + fis.getVariable("OUT_ballast_dropping").getMembership("val_40") + "\n" +
                "val_60 : " + fis.getVariable("OUT_ballast_dropping").getMembership("val_60") + "\n" +
                "val_80 : " + fis.getVariable("OUT_ballast_dropping").getMembership("val_80") + "\n" +
                "val_100 : " + fis.getVariable("OUT_ballast_dropping").getMembership("val_100") + "\n\n"
        );
    }

    public String getResult() {
        return new String("Wynik defuzyfikacji : " +
                fis.getVariable("OUT_ballast_dropping").getLatestDefuzzifiedValue() + "\n\n");
    }

    public FIS getFis() {
        return fis;
    }

}
