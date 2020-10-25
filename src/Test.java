import gnu.io.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Test {

    private static final int BOUND_RATE = 115200;

    public static void main(String[] args) {
        List<PortInfo> portInfos = getPortName();

        for(PortInfo info : portInfos) {
            System.out.println(info.toString());
        }


    }

    private static List<PortInfo> getPortName() {
        Enumeration<CommPortIdentifier> portList= CommPortIdentifier.getPortIdentifiers();
        List<PortInfo> portNameList = new ArrayList<>();

        while(portList.hasMoreElements()) {
            CommPortIdentifier temp = portList.nextElement();
            portNameList.add(new PortInfo(temp.getName(), temp.getCurrentOwner(), temp.getPortType()));
        }

        return portNameList;
    }
}

class PortInfo {
    String portName;
    String currentOwner;
    int portType;

    public PortInfo(String portName, String currentOwner, int portType) {
        this.portName = portName;
        this.currentOwner = currentOwner;
        this.portType = portType;
    }

    public String toString() {
        String info = String.format("Port Name: %s\nCurrent Owner: %s\n Port Type: %d\n\n", this.portName, this.currentOwner, this.portType);
        return info;
    }
}