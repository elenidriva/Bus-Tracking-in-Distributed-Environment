import java.io.Serializable;

class Topic implements Serializable {
    private String busLine;

    Topic(String LineID){
        this.busLine = LineID;
    }

    String getLineID() {
        return busLine;
    }
}