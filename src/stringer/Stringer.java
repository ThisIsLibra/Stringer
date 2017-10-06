/*
 * Copyright (C) 2017 Max 'Libra' Kersten
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package stringer;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 *
 * @author Max 'Libra' Kersten
 */
public class Stringer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Stringer made by Max 'Libra' Kersten \n");
        //Show usage of the program if there are 0 arguments provided
        if (args.length <= 0) {
            showUsage();
        }

        //Loop through the amount of given strings
        for (int i = 0; i < args.length; i++) {
            //Announce possible conversions
            System.out.println("Possible conversions:");
            System.out.println();
            showInfo(args[i]);
            //Announce hash pattern checking
            System.out.println("Hash pattern checking results:");
            possibleHash(args[i]);
            System.out.println();
        }
    }

    /**
     * Shows the usage of the program, the possible decoding methods and the
     * possible hashes that are matched.
     */
    private static void showUsage() {
        System.out.println("[Stringer] Provide the strings that need to be tested as an argument, separated by a space.");
        System.out.println("           Arguments that contain a space should be encapsulated between quotation marks \"such as these\".\n");
        System.out.println("           For example: java -jar String.jar [argument1] [argument2] [\"argument 3\"]\n");
        System.out.println("[Stringer] Currently, the following patterns are possibly decoded:");
        System.out.println("           base64, hex2ASCII, ROT-1 through ROT-25");
        System.out.println("[Stringer] If a field is left empty, no possible solution was found.\n");
        System.out.println("[Stringer] Based on the length of the string, possible matching hashes are detected.\n"
                + "           Currently the following hashing patterns are possibly detected:");
        System.out.println("           Adler32, CRC32, DES, TIGER-128, RIPEMD-128, HAVAL-128, MD4, MD5, APACHE-HTPASSWD, TIGER-160, RIPEMD-160, \n"
                + "           SHA-1, TIGER-192, blowfish, SNEFRU, GHOST, SHA-256, SHA-384, WHIRLPOOL and SHA-512");
    }

    /**
     * Shows the possible decoded values of the given input. Output is given in
     * the console.
     *
     * @param input the string which should be tested
     */
    private static void showInfo(String input) {
        //In case the user has entered multiple strings, the current treated input is shown before the decoded versions are shown
        System.out.println("Input: " + input + "\n");

        //Output the input as if it were base64
        System.out.println("base64: " + base64(input));

        //Output the input as if it were hexadecimal
        System.out.println("hexadecimal: " + hexadecimal(input));

        //Output the input as if it were ROT-n where 0 < n < 26
        for (int i = 1; i < rotN(input, false).size(); i++) {
            String output = "ROT-" + (26 - i) + ": " + rotN(input, false).get(i);
            System.out.println(output);
        }
        System.out.println();
    }

    /**
     * Matches the strings based on the pattern (length) of popular hashing
     * algorithms. Output is given in the console.
     *
     * @param input the string to be tested
     */
    private static void possibleHash(String input) {
        switch (input.length()) {
            //Check if the length equals the length of a Adler32 or CRC32 hash
            case 8:
                System.out.println("Possible Adler32 or CRC32 hash");
                break;
            //Check if the length equals the length of a DES hash
            case 13:
                System.out.println("Possible DES hash");
            //Check if the length equals the length of a TIGER-128, RIPEMD-128, HAVAL-128, MD4 or MD5 hash    
            case 32:
                System.out.println("Possible TIGER-128, RIPEMD-128, HAVAL-128, MD4 or MD5 hash");
                break;
            //Check if the length equals the length of a APACHE-HTPASSWD hash
            case 37:
                System.out.println("Possible APACHE-HTPASSWD hash");
            //Check if the length equals the length of a TIGER-160, RIPEMD-160 or SHA-1 hash
            case 40:
                System.out.println("Possible TIGER-160, RIPEMD-160 or SHA-1 hash");
                break;
            //Check if the length equals the length of a TIGER-192 hash
            case 47:
                System.out.println("Possible TIGER-192 hash");
            //Check if the length equals the length of a blowfish hash
            case 60:
                System.out.println("Possible blowfish hash");
            //Check if the length equals the length of a SNEFRU, GHOST or SHA-256 hash
            case 64:
                System.out.println("Possible SNEFRU, GHOST or SHA-256 hash");
            //Check if the length equals the length of a SHA-384 hash
            case 96:
                System.out.println("Possible SHA-384 hash");
            //Check if the length equals the length of a WHIRLPOOL or SHA-512 hash
            case 128:
                System.out.println("Possible WHIRLPOOL or SHA-512 hash");
            default:
                System.out.println("No pattern was matched");
                break;
        }
    }

    /**
     * Returns the inputted value by decoding as base64
     *
     * @param input the value to be decoded
     * @return the decoded value or nothing if the conversion fails
     */
    private static String base64(String input) {
        try {
            return new String(Base64.getDecoder().decode(input));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Returns the ASCII value of a hexadecimal string
     * Source:https://www.mkyong.com/java/how-to-convert-hex-to-ascii-in-java/
     * edited partially by Max 'Libra' Kersten
     *
     * @param input the hexadecimal value to convert
     * @return the ASCII value if succesful, upon encountering an error, nothing
     * will be returned
     */
    private static String hexadecimal(String input) {
        try {
            StringBuilder sb = new StringBuilder();

            //Split into two characters 49, 20, 4c...
            for (int i = 0; i < input.length() - 1; i += 2) {

                //Grab the hex in pairs of two
                String output = input.substring(i, (i + 2));
                //Convert the decimal to a character
                sb.append((char) Integer.parseInt(output, 16));
            }

            return sb.toString();
        } catch (Exception e) {
            return "";
        }

    }

    /**
     * Returns a list with Strings which are rotated ROT-n. n = 26 - listIndex
     *
     * Source: http://www.rot-n.com/?page_id=4 edited/converted by Max 'Libra'
     * Kersten
     *
     * @param input the string to mutate
     * @param numeric include numeric values
     * @return a list with mutated strings
     */
    private static List<String> rotN(String input, boolean numeric) {
        List<String> output = new ArrayList<>();
        for (int n = 0; n < 26; n++) {
            String result = "";
            int length = input.length();

            for (int i = 0; i < length; i++) {
                char ascii = input.charAt(i);
                char rotated = ascii;
                //Capital letters are 60 to 90
                if (ascii > 64 && ascii < 91) {
                    rotated = (char) (rotated + n);
                    if (rotated > 90) {
                        rotated += -90 + 64;
                    }
                    if (rotated < 65) {
                        rotated += -64 + 90;
                    }
                } else if (ascii > 96 && ascii < 123) { //Lowercase letters are between 97 and 122
                    rotated = (char) (rotated + n);
                    if (rotated > 122) {
                        rotated += -122 + 96;
                    }
                    if (rotated < 97) {
                        rotated += -96 + 122;
                    }
                }
                //Numeric values are between 48 to 57 
                if (numeric && ascii > 47 && ascii < 58) {
                    rotated = (char) (rotated + n);
                    if (rotated > 47) {
                        rotated += -57 + 47;
                    }
                    if (rotated < 58) {
                        rotated += -47 + 57;
                    }
                }
                result += (char) rotated;
            }
            output.add(result);
        }
        return output;
    }
}
