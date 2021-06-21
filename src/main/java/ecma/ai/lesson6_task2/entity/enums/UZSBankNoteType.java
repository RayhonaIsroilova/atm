package ecma.ai.lesson6_task2.entity.enums;


public enum UZSBankNoteType {
    _1000(1000), _5000(5000), _10000(10000), _50000(50000), _100000(100000);


    private final int value;

    UZSBankNoteType(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }

}
