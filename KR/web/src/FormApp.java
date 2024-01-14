
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
public class FormApp extends JFrame{
    private JButton button = new JButton("Отправить");
    private JLabel label1 = new JLabel("Имя:");
    private JLabel label2 = new JLabel("Фамилия:");
    private JLabel label3 = new JLabel("email:");
    private JTextField input1 = new JTextField("",5);
    private JTextField input2 = new JTextField("",5);
    private JTextField input3 = new JTextField("",5);

    public FormApp(){
        super("Форма");
        this.setBounds(100,100,300,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(4,2,2,2));
        container.add(label1);
        container.add(input1);
        container.add(label2);
        container.add(input2);
        container.add(label3);
        container.add(input3);
        button.addActionListener(new ButtonEvent());
        container.add(button);
    }
    class ButtonEvent implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String message = "";
            message += "имя: " + input1.getText() + "\nФамилия: " + input2.getText() + "\nemail: "+ input3.getText();
            JOptionPane.showMessageDialog(null,message,"Output",JOptionPane.PLAIN_MESSAGE);

            try {
                URL url = new URL("http://127.0.0.1:8000");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                String params = "name:"+input1.getText()+";surname:"+input2.getText()+";email:"+input3.getText();
                byte[] postData = params.getBytes(StandardCharsets.UTF_8);

                try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                    wr.write(postData);
                }

                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println(inputLine);
                    }
                }
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        }
    }
}

//"http://127.0.0.1/name:"+input1.getText()+";surname:"+input2.getText()+";email:"+input3.getText()