import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.fingerprint.IBitFingerprint;
import org.openscience.cdk.fingerprint.PubchemFingerprinter;
import org.openscience.cdk.inchi.InChIGeneratorFactory;
import org.openscience.cdk.inchi.InChIToStructure;
import org.openscience.cdk.interfaces.*;

import java.util.BitSet;
import java.util.Scanner;

/**
 * Created by benjaminzhang on 12/07/2017.
 * Copyright © benjaminzhang 2017.
 */
public class Test {
    private PubchemFingerprinter fprinter = new PubchemFingerprinter(DefaultChemObjectBuilder.getInstance());
    private IBitFingerprint fingerprint;

    private Test(String inchi){
        try {
            InChIGeneratorFactory factory = InChIGeneratorFactory.getInstance();
            InChIToStructure inChIToStructure = factory.getInChIToStructure(inchi, DefaultChemObjectBuilder.getInstance());
            IAtomContainer atomContainer = inChIToStructure.getAtomContainer();
            fingerprint = fprinter.getBitFingerprint(atomContainer);
        } catch (CDKException e) {
            e.printStackTrace();
        }
    }

    private BitSet getFingerprintBitSet(){
        return fingerprint.asBitSet();
    }

    private String getFingerPrintString(){
        StringBuffer stringBuffer = new StringBuffer();
        int[] setBitArray = fingerprint.getSetbits();
        int setBitArrayIndex = 0;

        for (int i = 0; i < fprinter.getSize(); i++) {
            if (setBitArrayIndex < setBitArray.length && setBitArray[setBitArrayIndex] == i) {
                stringBuffer.append('1');
                setBitArrayIndex++;
            }
            else stringBuffer.append('0');
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Please input InChI:");
        String inputInChi = input.nextLine();
        Test test = new Test(inputInChi);
        System.out.println("\nConvert InChI to PubChem Fingerprint BitSet:");
        System.out.println(test.getFingerprintBitSet());
        System.out.println("\nConvert InChI to PubChem Fingerprint:");
        System.out.println(test.getFingerPrintString());
    }
}
