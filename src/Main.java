import java.io.*;

/**
 * Created by benjaminzhang on 12/07/2017.
 * Copyright © benjaminzhang 2017.
 */
public class Main {
    private String readFileName = new String("/Users/benjaminzhang/Downloads/Smiles2.txt");
    private String writeFileName = new String("/Users/benjaminzhang/Downloads/PubChemFingerprint.txt");
    private StringBuffer stringBuffer = new StringBuffer();

    public void Run(){
        File file = new File(readFileName);
        Reader reader = null;

        try {
            //System.out.println("以字符为单位读取文件内容，一次读一个字节：");
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            int singleQuoteMark = 0;
            writeFileFromFileEnd("[");
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，rn这两个字符在一起时，表示一个换行。但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉r，或者屏蔽n。否则，将会多出很多空行。
                if (((char) tempchar) != 'r' && (char) tempchar != ',' && (char) tempchar != ' ') {
                    System.out.print("Reading...");
                    if ((char) tempchar == '\'')    singleQuoteMark++;
                    else    stringBuffer.append((char) tempchar);
                    if (singleQuoteMark == 2) {
                        singleQuoteMark = 0;
                        InChIToPubChemFingerprint inChIToPubChemFingerprint = new InChIToPubChemFingerprint(stringBuffer.toString());
                        writeFileFromFileEnd(inChIToPubChemFingerprint.getFingerprintBitSetString()+", ");
                        stringBuffer.delete(0,stringBuffer.length());
                        System.out.println("\nreadFileStatus = true");
                    }
                }
            }
            reader.close();
            writeFileFromFileEnd("]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 追加文件尾部：使用RandomAccessFile
     */
    protected void writeFileFromFileEnd(String content) {
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(writeFileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.Run();
    }
}
