Note: Java and hadoop are required to run this program. Should you have questions regarding how to perform a step in this file, consult "Assignment 3.pdf" which has tutorials regarding how to compile and run hadoop programs.

NOTE: Your input must be in the format of:
"
# x y z
# x y
# x y z a
"
where # is a peer id, and xyz... are ids of neighbors of that peer. It may be separated into multiple files if you desire, this will allow it to be run across more peers.

If you are using the remote linux machines provided for this lab, first connect to them via ssh and scp. They can be found at "zoidberg.cs.ndsu.nodak.edu" PuTTY can be used for ssh, and WinSCP can be used for scp on a windows machine. Alternatively, these functionalities should be available from command line.

If using the remote station, you will want to first connect with SCP and transfer the java file to the remote station. If this is the case, you will want to transfer the input files to an input folder on the remote station at this point as well.

Compile the program - "hadoop com.sun.tools.javac.Main PeerFinder.java", followed by "jar cf pf.jar PeerFinder*.class" on linux.

If you have not yet placed your input files in an input folder, do so now.

3. Run the program with the arguments "input" "output" where input is the folder with your inputs, and output is the file where you wish your output to be placed. On linux, this will be "hadoop jar pf.jar PeerFinder /input /output", where /input is the path to the input folder, and /output is the path to the output folder.

4. Your output is now in the output file.