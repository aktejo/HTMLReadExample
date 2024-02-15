import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/*

project: build a "link-puller"
in terms of screen parsing, how can we find links?
    through a href
how can we find where the link ends?
    through </a>?

*****
build gui:
    enter a website
    enter a search term
    display all links on website that mention that search term
*****

 */

public class HTMLRead implements ActionListener {

    public static void main(String[] args) {
        HTMLRead html = new HTMLRead();
    }
    private JFrame mainFrame;
    private JPanel newPanel;
    private JTextField searchBox;
    private JTextArea searchResults;
    private JButton goButton;
    private JTextField website;
    private JScrollPane scrollPane;

    String searchTerm = "tennis";
    String websiteAddress = "https://www.milton.edu/";

    public HTMLRead() {
        mainFrame = new JFrame();
        searchBox = new JTextField("milton");
        goButton = new JButton("Go");
        newPanel = new JPanel();
        searchResults = new JTextArea("Results will populate here");
        website = new JTextField("https://www.milton.edu/athletics/");
        goButton.addActionListener(this);
        scrollPane = new JScrollPane(searchResults);

        searchResults.setEditable(false);
        mainFrame.setLayout(new BorderLayout(10, 10));
        newPanel.setLayout(new FlowLayout());

        mainFrame.add(newPanel, BorderLayout.NORTH);
        mainFrame.add(scrollPane, BorderLayout.CENTER);
        newPanel.add(website);
        newPanel.add(searchBox);
        newPanel.add(goButton);


        mainFrame.pack();
        mainFrame.setSize(1600, 800);
        website.setSize(100, 100);
        searchBox.setSize(100, 100);
        mainFrame.setVisible(true);

        populateResults();

    }
    public void populateResults() {
        try {
            System.out.println("populateResults ran");
            URL url = new URL(websiteAddress);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("a href")) {
                    if (line.contains(searchTerm)) {
                        int index = line.indexOf("a href");
                        String differentString = line.substring((index + 8));
                        System.out.println(differentString);
                        int nextQuote = differentString.indexOf("\"");
                        String link = differentString.substring(0, (nextQuote));
                        searchResults.setText((searchResults.getText() + "\n" + index + ": " + link));
                        System.out.println(index + ": " + link);
                    }
                }
            }

            reader.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object buttonClicked = e.getSource();
        if (buttonClicked == goButton) {
            searchTerm = searchBox.getText();
            websiteAddress = website.getText();
            System.out.println("buttonclicked");
            searchResults.setText("");
            populateResults();
        }

    }

}
