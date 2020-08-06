package LinkedLIst;

public class Literal {
    private int exponent;
    private int coefficient;
    public Literal(int coef, int exp){
        exponent=exp;
        coefficient=coef;
    }
    public int getCoefficient() {
        return coefficient;
    }

    public int getExponent() {
        return exponent;
    }
}
