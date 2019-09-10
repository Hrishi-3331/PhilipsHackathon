package com.hackathon.philips.dare2complete.philips.Objects;

public class Medicine {

    private String name;
    private String instruction;
    private boolean morning;
    private boolean afternoon;
    private boolean evening;

    public Medicine() {
    }

    public Medicine(String name, String instruction, boolean morning, boolean afternoon, boolean evening) {
        this.name = name;
        this.instruction = instruction;
        this.morning = morning;
        this.afternoon = afternoon;
        this.evening = evening;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instructions) {
        this.instruction = instructions;
    }

    public boolean isMorning() {
        return morning;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    public boolean isAfternoon() {
        return afternoon;
    }

    public void setAfternoon(boolean afternoon) {
        this.afternoon = afternoon;
    }

    public boolean isEvening() {
        return evening;
    }

    public void setEvening(boolean evening) {
        this.evening = evening;
    }
}
