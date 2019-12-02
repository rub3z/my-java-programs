package rational;

public class Rational {

   private int mNumerator;
   private int mDenominator;

   public Rational(int numerator, int denominator) {
      mNumerator = numerator;
      mDenominator = denominator;
      if (denominator < 0) {
         mDenominator = -mDenominator;
         mNumerator *= -1;
      }
      else if (denominator == 0) {
         mDenominator = 1;
      }
   }

   public int getNumerator() {
      return mNumerator;
   }

   public int getDenominator() {
      return mDenominator;
   }

   public Rational add(Rational other) {
      Rational sum = new Rational(mNumerator * other.mDenominator
       + mDenominator * other.mNumerator,
       mDenominator * other.mDenominator);
      return sum;
   }

   public Rational negate() {
      Rational neg = new Rational(-mNumerator, mDenominator);
      return neg;
   }

   public Rational subtract(Rational other) {
      return add(other.negate());
   }

   public String toString() {
      if (mDenominator != 1) {
         return mNumerator + "/" + mDenominator;
      }
      return mNumerator + "";
   }

   public Rational multiply(Rational other) {
      Rational product = new Rational(mNumerator * other.mNumerator,
       mDenominator * other.mDenominator );
      return product;
   }
   
   public Rational reciprocal() {
      Rational flipThisRational = new Rational(mDenominator, mNumerator);
      return flipThisRational;
   }
   
   public Rational divide(Rational other) {
      return multiply(other.reciprocal());
   }
}
