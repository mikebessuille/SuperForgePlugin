package Henry.Bessuille.Minecraft;

/**
 * Created by mbessuille on 15-01-06.
 * Mike created this purely to test Klocwork errors.
 */
public class KWTest
{

    // KW:  Method should start with lowercase letter
    void KlocworkTest()
    {
        int x = 0; // KW: unused
        String myString = null;

        for( int y = 0; y > 0; y++ )
        {
            // KW: String never read after assigned
            // KW: unnecessary new.  Can be replaced with a string expression.
            myString = new String("You Stupid Moron");

            // KW: isn't this an infinite loop?
            // Sadly, there isn't an infinite loop checker for Java yet!!! :(
        }

        int z = 0;
        int k = 0;
        while( true )
        {
            if( z > 10 )
                break;
            k++;
        }

        float f1 = (float) 1.003;
        float f2 = (float) 1.004;
        // KW:  Don't compare floats, because of imprecision.
        if( f1 == f2 )
            return;

        return;
    }
}
