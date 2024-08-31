/*
    File name:  GC_EGames_GUI.java
    Purpose:    Create the GUI window Application
                provide all functionalities of the application
    Author:     Jeetendra Karki
    Date:       18 August
    Version:    1.0
    Notes:      Functionalities

    Required Functionalities
    1.   APPLICATION IS LAUNCHED
    1.1  READ-IN COMPETITION RESULTS FROM COMPETITIONS.CSV external file
        file and set up in ArrayList<Competition> competitionList data structure---- Done
    1.2 Read-in team names and deails from teams.csv external file and set up in ArrayList<Team>
        teamlist data structure--- to do
    1.3 Display read-in competition results in JTable----- done
    1.4 Display read-in team names in 2 x JComboBoxes-------to do
    1.5 Display team info for selected team name in update panel-------to do 

    2.0 ADD A NEW COMPETITION RESULT
    2.1 Add a new (Validated) competition result to ArrayList< Competition> competitionList
        and display a JTable-----to do
    3.0 ADD A NEW TEAM
    3.1 Add a new (Validated) team to ArrayList<Team> team List and add to the 2 x JComboBoxes----- to do ( partially done)

    4.0 UPDATE AN EXISTING TEAM
    4.1 Update details for a selected (existing) team validate changes to person. email
        and change value in ArrayList <Team> for the selected team------to do

    5.0 DISPLAY TOP TEAMS IN THE LEADERBOARD TABLE
    5.1 Calculate total points earned for each team in ArrayList<Team> teamList------to do
    5.2 List the tamList and the total points in total points descending order (Highest)

    6.0 APPLICATION IS CLOSED AND SAVING OF DATA
    6.1 WHEN application is closed provide option for user to save changes (competitionList, teamList)--- done
    6.2 Save (write) to 2 x external csv files the data from: ------- to do
        ArrayList< Competition> competitionList-----------> competitions.csv
        ArrayList<Team> teamList --------> team.csv
 */
package gc_egames_gui;





// import Statements
import gc_egames_gui.Competition;
import gc_egames_gui.Team;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;




public class GC_EGames_GUI extends javax.swing.JFrame 
{
    //private data
    // for storing comp results
    private ArrayList<Competition> competitionList;
    // for storing team data
    private ArrayList<Team> teamList;
    // boolean to track the item selectionchanges to the JComboBoxes
    // workaround for when data is added to the JComboBoxes during launch
    boolean comboBoxStatus;
    // for customising the JTable( which displays comp results)
    private DefaultTableModel compResultsTableModel;
    
    
    

    /**
     * Creates new form GC_EGames_GUI
     */
    public GC_EGames_GUI() 
    {
       /*************1. INITIALISE PRIVATE DATA FIELDS****************/
        competitionList = new ArrayList<Competition>();
        teamList = new ArrayList<Team>();
        comboBoxStatus = false;
        compResultsTableModel = new DefaultTableModel();
        
        
        /************2. CUSTOMISE TABLE MODEL ***********************/
        // customised column names for movie JTable
        String [] columnNames_Results = new String[]
        {"Date", "Location", "Game", "Team", "Points"};

        //set up customisation
        compResultsTableModel.setColumnIdentifiers(columnNames_Results);
        
        /****************3. INITIALISE ALL SWING CONTROLS *****************/  
        
            initComponents();
        
         /****************4. CUSTOMISE TABLE COLUMN IN JTABLE *****************/
            resizeTableColumns();
            
          /****************5. READ IN EDTERNAL CSV FILES *****************/ 
            readCompetitionData();
            readTeamData();
          
           /****************6. DISPLAY COMPETITION DATA IN JTABLE *****************/ 
           displayCompetitions();
           
            /****************7. INITIALISE ALL SWING CONTROLS *****************/
            displayTeams();
            
            
            
             /****************8. DISPLAY TEAM DETAILS IN JTEXTFIELDS WITH TEAM COMBO BOXES *****************/ 
            displayTeamDetails();
            
            
            
            // rest comboBoxStatus to true (finished with updating JComboBoxes)
            comboBoxStatus = true;
          
          
         
    }
    
    private void resizeTableColumns()
    {
        // Columns: "Date", "Location", "Games", "Team", "Points"
        // (Total numeric values must = 1)
        double[] columnWidthPercentage = {0.2f, 0.2f, 0.3f, 0.2f, 0.1f};
        int tableWidth = compResults_Table.getWidth();
        TableColumn column;
        TableColumnModel tableColumnModel = compResults_Table.getColumnModel();
        int cantCols = tableColumnModel.getColumnCount();
        for (int i = 0; i < cantCols; i++)
        {
            column = tableColumnModel.getColumn(i);
            float pWidth = Math.round(columnWidthPercentage[i] * tableWidth);
            column.setPreferredWidth((int)pWidth);
            
            
        }
    }
    
    /***********************************************************************
     Method: readCompetition()
     Purpose: reads in the competitions.csv file and 
             populates object created from the competition class
     Inputs:  void 
     Output:  Void
    
    /***********************************************************************/
    
    
    private void readCompetitionData()
    {
     try
     {
         //1. Create reader and designate external file to read from
         FileReader reader = new FileReader("competitions.csv");
         // 2. Create bufferedReader which use the reader object
         BufferedReader bufferedReader = new BufferedReader(reader);
         // 3. Declare line string (used to read from and store each line that is read)
         String line;
         // 4. Loop through each line in the external file
         //     until end of file is reached (EOF)
         while ((line = bufferedReader.readLine()) != null)
         {
             // Check line 
             //System.out.println(line);
             if (line.length() > 0)
             {
                 // split the line string by the delimiter comma character
                 // "League of Legends" is set up in lineArray[0]
                 // "Tafe Commera" is set up in lineArray [1]
                 // "23-Aug-2024" is set up in lineArray[2]
                 
                 String [] lineArray = line.split(",");
                 // set up individual variables for each split line component
                 String game = lineArray[0];
                 String location = lineArray[1];
                 String compDate = lineArray[2];
                 String team = lineArray[3];
                 // " 2" is converted to an actal integer
                 int points = Integer.parseInt(lineArray[4]);
                 // create the competition instance
                 Competition comp = new Competition(game, location, compDate, team, points);
                 // add comp to the ArrayList
                 competitionList.add(comp);
                 
                         
             }
             
         }
         // 5 Close the reader object
         reader.close();
     }
     catch (FileNotFoundException fnfe)
     {
         // catch any file not found
         System.out.println("ERROR: competitions.csv file not found");
     }
     catch (IOException ioe)
     {
         // catch any file not found
         System.out.println("ERROR: Read problem with competition.csv file");
     }
     catch (NumberFormatException nfe)
     {
         // catch number string conversatoion to integer exception
         System.out.println("Error: Number format exceptionfor integer points");
     }
     
    }
  
    private void readTeamData()
    {
        try
     {
         //1. Create reader and designate external file to read from
         FileReader reader = new FileReader("teams.csv");
         // 2. Create bufferedReader which use the reader object
         BufferedReader bufferedReader = new BufferedReader(reader);
         // 3. Declare line string (used to read from and store each line that is read)
         String line;
         // 4. Loop through each line in the external file
         //     until end of file is reached (EOF)
         while ((line = bufferedReader.readLine()) != null)
         {
             // Check line 
             //System.out.println(line);
             if (line.length() > 0)
             {
                 // split the line string by the delimiter comma character
                 // "League of Legends" is set up in lineArray[0]
                 // "Tafe Commera" is set up in lineArray [1]
                 // "23-Aug-2024" is set up in lineArray[2]
                 

                 
                 String [] lineArray = line.split(",");
                 // set up individual variables for each split line component
                 String teamName = lineArray[0];
                 String contactName = lineArray[1];
                 String contactPhone = lineArray[2];
                 String contactEmail = lineArray[3];
                 
                
                 // create the Team instance
                 Team team = new Team(teamName, contactName, contactPhone, contactEmail);
                 // add team to the ArrayList
                 teamList.add(team);
                 
                         
             }
             
         }
         // 5 Close the reader object
         reader.close();
     }
     catch (FileNotFoundException fnfe)
     {
         // catch any file not found
         System.out.println("ERROR: teams.csv file not found");
     }
     catch (IOException ioe)
     {
         // catch any file not found
         System.out.println("ERROR: Read problem with teams.csv file");
     }
     
    }
    /***********************************************************************
     Method: displayCompetition()
     Purpose: display Competitions from the ArrayList in the JTable 
     Inputs:  void 
     Output:  Void
    
    /***********************************************************************/
    
    private void displayCompetitions()
    {
        // populate competition data into JTable
        if (competitionList.size() > 0)
        {
            // create Object [][] 2- D array for JTable
            Object[][] comp2DArray = new Object[competitionList.size()][];
            // populate 2-D array with the competition
            for (int i = 0; i < competitionList.size(); i++)
            {
                // create single Dimensional object [] array----1 competition (for row)
                Object [] comp = new Object [5];
                // date 
                comp [0] = competitionList.get(i).getCompetitionDate();
                // location 
                comp[1] = competitionList.get(i).getLocation();
                // game
                comp[2] = competitionList.get(i).getGame();
                // team
                comp[3] = competitionList.get(i).getTeam();
                // points
                comp[4] = competitionList.get(i).getPoints();
                // append comp to the 2 D array comp 2DArray 
                comp2DArray[i] = comp;
                
            }
            
            // remove all existing rows in the JTable ( if there are any)
            if (compResultsTableModel.getRowCount() > 0)
            {
                for (int i = compResultsTableModel.getColumnCount() -1; i >= 0; i--)
                {
                    compResultsTableModel.removeRow(i);
                }
            }
            // put 2D Array of competition data into JTable
            if(comp2DArray.length > 0)
            {
                // add data to tableModel
                for (int row = 0; row < comp2DArray.length; row++)
                {
                    compResultsTableModel.addRow(comp2DArray[row]);
                    
                }
            }
            
            // updata table Model
            compResultsTableModel.fireTableDataChanged();
        }
    }

    private void displayTeams()
    {
        // populate the team name from team ArrayList<Team>
        // into the 2 JComboBoxes
        // newCompResult_ComboBox
        // updateTeam_ComboBox
        // checks if there are items in the combo boxes
        // if any, remove them
       
        if (newCompResult_ComboBox.getItemCount() > 0) {
            newCompResult_ComboBox.removeAllItems();
        }
    
        if (updateTeam_ComboBox.getItemCount() > 0) {
            updateTeam_ComboBox.removeAllItems();
        }
        
        if (teamList.size() > 0) {
                for (int i = 0; i < teamList.size(); i++) {
                    System.out.println(teamList.get(i).getTeamName());
                    newCompResult_ComboBox.addItem(teamList.get(i).getTeamName());
                    updateTeam_ComboBox.addItem(teamList.get(i).getTeamName());
                }
                System.out.println(newCompResult_ComboBox);
        }
       
    }


    private void displayTeamDetails()
    {   
        //this method is called when the user selects a team name from the JComboBox that is in the UPDATE EXISTING TEAM

        String selectedTeam = ( String) updateTeam_ComboBox.getSelectedItem();
        for (Team team : teamList)
        {
            System.out.println(team.getTeamName());
            if (team.getTeamName().equals(selectedTeam))
                {
                    updateContactPerson_TextField.setText(team.getContactName());
                    updateContactPhone_TextField.setText(team.getContactPhone());
                    updateContactEmail_TextField.setText(team.getContactEmail());    
                
                    break;
                }
        }
        
    
    }
    
    
    /*******************************************************************
     Method:    validateNewTeam()
     Purpose:   Basic validation of user inputs when creating a new team
     *          Uses Boolean validation to track the status of the validation
     *          Uses JOption to create a pop-up window if validation is false
     *          to advise user of errors
     Inputs:    void
     Outputs:   returns Boolean validation (true if all fields contain String, False if any empty
     *******************************************************************/
    private boolean validateNewTeam()
    {
        boolean validation = true;
        String errorMsg = "Error(s) encountered !\n";
        
        if (newTeamName_TextField.getText().isEmpty())
        {
            errorMsg += "New team name required\n";
            validation = false;
            
        }
        if (newContactPerson_TextField.getText().isEmpty())
        {
         errorMsg += "Contact person required\n";
         validation = false;
        }
        if (newContactPhone_TextField.getText().isEmpty())
        {
            errorMsg += "Contact phone number required\n";
            validation = false;
        }
        if (newContactEmail_TextField.getText().isEmpty())
        {
            errorMsg += "Contact Email address required\n";
            validation = false;
        }
        if (validation == false)
        {
            JOptionPane.showMessageDialog(null, errorMsg, "ERROR(s)", JOptionPane.ERROR_MESSAGE);
        }
        
        return validation;
        
        
    }
    
    
    /*******************************************************************
     Method:    validateNewCompResult()
     Purpose:   Basic validation of user inputs when creating a new Competition
     *          Uses Boolean validation to track the status of the validation
     *          Uses JOption to create a pop-up window if validation is false
     *          to advise user of errors
     Inputs:    void
     Outputs:   returns Boolean validation (true if all fields contain String, False if any empty
     *******************************************************************/
    private boolean validateNewCompResult()
    {
        boolean validation = true;
        String errorMsg = "Error(s) encountered !\n";
        
        if (newCompDate_TextField.getText().isEmpty())
        {
            errorMsg += "Date required\n";
            validation = false;
        }       
        if (newCompLocation_TextField.getText().isEmpty())
        {
         errorMsg += "Location required\n";
         validation = false;
        }
        if (newCompGame_TextField.getText().isEmpty())
        {
            errorMsg += "New Game required\n";
            validation = false;
            
        }
        
        if (newCompResult_ComboBox.getSelectedItem() == null )
        {
            errorMsg += "Competition Team required\n";
             validation = false;
        }

        if (newCompPoints_TextField.getText().isEmpty())
        {
            errorMsg += "Compition Points required\n";
            validation = false;
        }
        if (validation == false)
        {
            JOptionPane.showMessageDialog(null, errorMsg, "ERROR(s)", JOptionPane.ERROR_MESSAGE);
        }
        
        return validation;
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header_Panel = new javax.swing.JPanel();
        img_Label = new javax.swing.JLabel();
        body_Panel = new javax.swing.JPanel();
        body_JTabbedPane = new javax.swing.JTabbedPane();
        compResult_JPanel = new javax.swing.JPanel();
        compResults_JLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        compResults_Table = new javax.swing.JTable();
        displayTopTeams_Button = new javax.swing.JButton();
        addNewCompResult_JPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        addDate_JLabel = new javax.swing.JLabel();
        newCompDate_TextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        newCompLocation_TextField = new javax.swing.JTextField();
        addGame_JLabel = new javax.swing.JLabel();
        newCompGame_TextField = new javax.swing.JTextField();
        addNewCompTeam_JLabel = new javax.swing.JLabel();
        newCompResult_ComboBox = new javax.swing.JComboBox<>();
        addNewCompPoints_JLabel = new javax.swing.JLabel();
        newCompPoints_TextField = new javax.swing.JTextField();
        addNewCompResult_Button = new javax.swing.JButton();
        addNewTeam_JPanel = new javax.swing.JPanel();
        titleAddNewteam_JPanel = new javax.swing.JLabel();
        newTeamName_JLabel = new javax.swing.JLabel();
        newTeamName_TextField = new javax.swing.JTextField();
        newContactPerson_JLabel1 = new javax.swing.JLabel();
        newContactPerson_TextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        newContactPhone_TextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        newContactEmail_TextField = new javax.swing.JTextField();
        addNewTeam_Button = new javax.swing.JButton();
        updateTeam_JPanel = new javax.swing.JPanel();
        addResultTitle_JLabel = new javax.swing.JLabel();
        addResultTeam_JLabel = new javax.swing.JLabel();
        updateTeam_ComboBox = new javax.swing.JComboBox<>();
        updateContactPerson_JLabel = new javax.swing.JLabel();
        updateContactPerson_TextField = new javax.swing.JTextField();
        updateContactPhone_JLabel = new javax.swing.JLabel();
        updateContactPhone_TextField = new javax.swing.JTextField();
        updateContactEmail_JLabel = new javax.swing.JLabel();
        updateContactEmail_TextField = new javax.swing.JTextField();
        updateExistingTeam_Button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GC EGames Application");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        img_Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/GoldCoastESports_Header.jpg"))); // NOI18N

        javax.swing.GroupLayout header_PanelLayout = new javax.swing.GroupLayout(header_Panel);
        header_Panel.setLayout(header_PanelLayout);
        header_PanelLayout.setHorizontalGroup(
            header_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(img_Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        header_PanelLayout.setVerticalGroup(
            header_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(img_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        body_Panel.setBackground(new java.awt.Color(255, 255, 255));
        body_Panel.setPreferredSize(new java.awt.Dimension(800, 478));

        compResult_JPanel.setBackground(new java.awt.Color(255, 255, 255));

        compResults_JLabel.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        compResults_JLabel.setText("Team Competition Results");

        compResults_Table.setModel(compResultsTableModel);
        jScrollPane1.setViewportView(compResults_Table);

        displayTopTeams_Button.setText("DISPLAY TOP TEAMS ");
        displayTopTeams_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayTopTeams_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout compResult_JPanelLayout = new javax.swing.GroupLayout(compResult_JPanel);
        compResult_JPanel.setLayout(compResult_JPanelLayout);
        compResult_JPanelLayout.setHorizontalGroup(
            compResult_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, compResult_JPanelLayout.createSequentialGroup()
                .addGroup(compResult_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, compResult_JPanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(compResults_JLabel))
                    .addGroup(compResult_JPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(displayTopTeams_Button)))
                .addGap(61, 61, 61))
            .addGroup(compResult_JPanelLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        compResult_JPanelLayout.setVerticalGroup(
            compResult_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(compResult_JPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(compResults_JLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(displayTopTeams_Button)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        body_JTabbedPane.addTab("Team Competition result", compResult_JPanel);

        addNewCompResult_JPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel1.setText("Add new competition result");

        addDate_JLabel.setText("Date:");

        jLabel3.setText("Location:");

        addGame_JLabel.setText("Game:");

        addNewCompTeam_JLabel.setText("Team:");

        newCompResult_ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Jeeten" }));

        addNewCompPoints_JLabel.setText("Points:");

        newCompPoints_TextField.setText("0");

        addNewCompResult_Button.setText("ADD NEW COMPETITION RESULT");
        addNewCompResult_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewCompResult_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addNewCompResult_JPanelLayout = new javax.swing.GroupLayout(addNewCompResult_JPanel);
        addNewCompResult_JPanel.setLayout(addNewCompResult_JPanelLayout);
        addNewCompResult_JPanelLayout.setHorizontalGroup(
            addNewCompResult_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewCompResult_JPanelLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(addNewCompResult_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(addNewCompResult_Button)
                    .addGroup(addNewCompResult_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addGroup(addNewCompResult_JPanelLayout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addGroup(addNewCompResult_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(addDate_JLabel)
                                .addComponent(jLabel3)
                                .addComponent(addGame_JLabel)
                                .addComponent(addNewCompTeam_JLabel)
                                .addComponent(addNewCompPoints_JLabel))
                            .addGap(33, 33, 33)
                            .addGroup(addNewCompResult_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(newCompDate_TextField)
                                .addComponent(newCompLocation_TextField)
                                .addComponent(newCompGame_TextField)
                                .addGroup(addNewCompResult_JPanelLayout.createSequentialGroup()
                                    .addComponent(newCompPoints_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(newCompResult_ComboBox, 0, 251, Short.MAX_VALUE)))))
                .addGap(292, 292, 292))
        );
        addNewCompResult_JPanelLayout.setVerticalGroup(
            addNewCompResult_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewCompResult_JPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addNewCompResult_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addDate_JLabel)
                    .addComponent(newCompDate_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addNewCompResult_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(newCompLocation_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addNewCompResult_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addGame_JLabel)
                    .addComponent(newCompGame_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(addNewCompResult_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addNewCompTeam_JLabel)
                    .addComponent(newCompResult_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addNewCompResult_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addNewCompPoints_JLabel)
                    .addComponent(newCompPoints_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68)
                .addComponent(addNewCompResult_Button)
                .addContainerGap(106, Short.MAX_VALUE))
        );

        body_JTabbedPane.addTab("Add new competition result", addNewCompResult_JPanel);

        addNewTeam_JPanel.setBackground(new java.awt.Color(255, 255, 255));

        titleAddNewteam_JPanel.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        titleAddNewteam_JPanel.setText("Add new team");

        newTeamName_JLabel.setText("Team Name:");

        newContactPerson_JLabel1.setText("Contact Person:");

        jLabel4.setText("Contact Phone:");

        jLabel5.setText("Contact Email:");

        addNewTeam_Button.setText("ADD NEW TEAM");
        addNewTeam_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewTeam_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addNewTeam_JPanelLayout = new javax.swing.GroupLayout(addNewTeam_JPanel);
        addNewTeam_JPanel.setLayout(addNewTeam_JPanelLayout);
        addNewTeam_JPanelLayout.setHorizontalGroup(
            addNewTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewTeam_JPanelLayout.createSequentialGroup()
                .addGroup(addNewTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addNewTeam_JPanelLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(titleAddNewteam_JPanel))
                    .addGroup(addNewTeam_JPanelLayout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addGroup(addNewTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(addNewTeam_Button)
                            .addGroup(addNewTeam_JPanelLayout.createSequentialGroup()
                                .addGroup(addNewTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(newTeamName_JLabel)
                                    .addComponent(newContactPerson_JLabel1)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(26, 26, 26)
                                .addGroup(addNewTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(newContactEmail_TextField, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                                    .addComponent(newContactPhone_TextField)
                                    .addComponent(newContactPerson_TextField)
                                    .addComponent(newTeamName_TextField))))))
                .addContainerGap(253, Short.MAX_VALUE))
        );
        addNewTeam_JPanelLayout.setVerticalGroup(
            addNewTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewTeam_JPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(titleAddNewteam_JPanel)
                .addGap(18, 18, 18)
                .addGroup(addNewTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newTeamName_JLabel)
                    .addComponent(newTeamName_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addNewTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newContactPerson_JLabel1)
                    .addComponent(newContactPerson_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addNewTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(newContactPhone_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addNewTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(newContactEmail_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(76, 76, 76)
                .addComponent(addNewTeam_Button)
                .addContainerGap(151, Short.MAX_VALUE))
        );

        body_JTabbedPane.addTab("Add new team", addNewTeam_JPanel);

        updateTeam_JPanel.setBackground(new java.awt.Color(255, 255, 255));

        addResultTitle_JLabel.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        addResultTitle_JLabel.setText("Update an existing team");

        addResultTeam_JLabel.setText("Team:");

        updateTeam_ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        updateTeam_ComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                updateTeam_ComboBoxItemStateChanged(evt);
            }
        });

        updateContactPerson_JLabel.setText("Contact Person:");

        updateContactPhone_JLabel.setText("Contact Phone:");

        updateContactEmail_JLabel.setText("Contact Email:");

        updateExistingTeam_Button.setText("UPDATE EXISTING TEAM");
        updateExistingTeam_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateExistingTeam_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout updateTeam_JPanelLayout = new javax.swing.GroupLayout(updateTeam_JPanel);
        updateTeam_JPanel.setLayout(updateTeam_JPanelLayout);
        updateTeam_JPanelLayout.setHorizontalGroup(
            updateTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updateTeam_JPanelLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(updateTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addResultTitle_JLabel)
                    .addGroup(updateTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(updateExistingTeam_Button)
                        .addGroup(updateTeam_JPanelLayout.createSequentialGroup()
                            .addGroup(updateTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(addResultTeam_JLabel)
                                .addComponent(updateContactPerson_JLabel)
                                .addComponent(updateContactPhone_JLabel)
                                .addComponent(updateContactEmail_JLabel))
                            .addGap(52, 52, 52)
                            .addGroup(updateTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(updateTeam_ComboBox, 0, 208, Short.MAX_VALUE)
                                .addComponent(updateContactPerson_TextField)
                                .addComponent(updateContactPhone_TextField)
                                .addComponent(updateContactEmail_TextField)))))
                .addContainerGap(290, Short.MAX_VALUE))
        );
        updateTeam_JPanelLayout.setVerticalGroup(
            updateTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updateTeam_JPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(addResultTitle_JLabel)
                .addGap(18, 18, 18)
                .addGroup(updateTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addResultTeam_JLabel)
                    .addComponent(updateTeam_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(updateTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateContactPerson_JLabel)
                    .addComponent(updateContactPerson_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(updateTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(updateContactPhone_JLabel)
                    .addComponent(updateContactPhone_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(updateTeam_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(updateContactEmail_JLabel)
                    .addComponent(updateContactEmail_TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(73, 73, 73)
                .addComponent(updateExistingTeam_Button)
                .addContainerGap(152, Short.MAX_VALUE))
        );

        body_JTabbedPane.addTab("Update an existing team", updateTeam_JPanel);

        javax.swing.GroupLayout body_PanelLayout = new javax.swing.GroupLayout(body_Panel);
        body_Panel.setLayout(body_PanelLayout);
        body_PanelLayout.setHorizontalGroup(
            body_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(body_PanelLayout.createSequentialGroup()
                .addComponent(body_JTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 705, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        body_PanelLayout.setVerticalGroup(
            body_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(body_JTabbedPane)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(body_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(header_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(body_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // 
    private void displayTopTeams_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayTopTeams_ButtonActionPerformed
        // TODO add your handling code here:
    
        // Create a Map to store the total points per team
        Map<String, Integer> teamPointsMap = new HashMap<>();

        // Calculate the total points per team
        for (Competition competition : competitionList) {
            String team = competition.getTeam();
            int points = competition.getPoints();

            // Update the total points for each team
            teamPointsMap.put(team, teamPointsMap.getOrDefault(team, 0) + points);
        }

        // Sort the teams by total points in descending order
        List<Map.Entry<String, Integer>> sortedTeams = new ArrayList<>(teamPointsMap.entrySet());
        sortedTeams.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Build the message to display the top 4 teams
        StringBuilder message = new StringBuilder("TEAMS LEADERBOARD:\n\n");
        message.append(String.format("%-10s %25s\n", "Points", "Teams"));
        message.append("-------------------------------------------\n");

        int maxTeams = Math.min(4, sortedTeams.size());
        for (int i = 0; i < maxTeams; i++) {
            Map.Entry<String, Integer> entry = sortedTeams.get(i);
            // Format the output with fixed-width columns
            message.append(String.format("%-10d %25s\n", entry.getValue(), entry.getKey()));
        }

        // Display the top teams in a JOptionPane
        JOptionPane.showMessageDialog(null, message.toString(), "TEAMS LEADERBOARD", JOptionPane.INFORMATION_MESSAGE);
        
        
    }//GEN-LAST:event_displayTopTeams_ButtonActionPerformed

    private void addNewCompResult_ButtonActionPerformed(java.awt.event.ActionEvent evt)
    {
//GEN-FIRST:event_addNewCompResult_ButtonActionPerformed
        // TODO add your handling code here:
        if (validateNewCompResult() == true) {
            // get new team data string values
            String newCompDate = newCompDate_TextField.getText();
            String newCompLocation = newCompLocation_TextField.getText();
            String newGame = newCompGame_TextField.getText();
            String newTeam = newCompResult_ComboBox.getSelectedItem().toString();
            String newPoints = newCompPoints_TextField.getText();
            
            int yesOrNo = JOptionPane.showConfirmDialog(null,
                    " You are about to add a New Competition Result for" + newTeam + "\n" +
            "Do you wish to proceed? Yes or No?", "Add new Competition Result",
            JOptionPane.YES_NO_OPTION);
            
            
            // check if yes or no
            if  (yesOrNo == JOptionPane.YES_OPTION)
            {
                // add the new competition to the ArrayList
                competitionList.add(new Competition(newGame, newCompLocation, newCompDate, newTeam, Integer.parseInt(newPoints)));
                
                // update the new competition list into JComboBoxes
                displayCompetitions();

                
            }
            else
            {
                //no action
            }
        }
    }//GEN-LAST:event_addNewCompResult_ButtonActionPerformed

    private void addNewTeam_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewTeam_ButtonActionPerformed
        // TODO add your handling code here:
        // check if text fields are validated 
        if (validateNewTeam() == true)
        {
            // get new team data string values
            String newTeamName = newTeamName_TextField.getText();
            String newContactPerson = newContactPerson_TextField.getText();
            String newContactPhone = newContactPhone_TextField.getText();
            String newContactEmail = newContactEmail_TextField.getText();
            
            int yesOrNo = JOptionPane.showConfirmDialog(null,
                    " You are about to add a new team for" + newTeamName + "\n" +
            "Do you wish to proceed? Yes or No?", "Add new team",
            JOptionPane.YES_NO_OPTION);
            
            
            // check if yes or no
            if  (yesOrNo == JOptionPane.YES_OPTION)
            {
                // add the new team to the ArrayList
                teamList.add(new Team(newTeamName, newContactPerson, newContactPhone, newContactEmail));
                // update the new team list into JComboBoxes
                displayTeams();
                
            }
            else
            {
                //no action
            }
        }
        
    }//GEN-LAST:event_addNewTeam_ButtonActionPerformed

    /*******************************************************************
     Method:    validateExistingTeam()
     Purpose:   Basic validation of user inputs when creating a new team
     *          Uses Boolean validation to track the status of the validation
     *          Uses JOption to create a pop-up window if validation is false
     *          to advise user of errors
     Inputs:    void
     Outputs:   returns Boolean validation (true if all fields contain String, False if any empty
     *******************************************************************/
    private boolean validateExistingTeam()
    {
        boolean validation = true;
        String errorMsg = "Error(s) encountered !\n";
        
        if (updateContactPerson_TextField.getText().isEmpty())
        {
         errorMsg += "Contact person required\n";
         validation = false;
        }
        if (updateContactPhone_TextField.getText().isEmpty())
        {
            errorMsg += "Contact phone number required\n";
            validation = false;
        }
        if (updateContactEmail_TextField.getText().isEmpty())
        {
            errorMsg += "Contact Email address required\n";
            validation = false;
        }
        if (validation == false)
        {
            JOptionPane.showMessageDialog(null, errorMsg, "ERROR(s)", JOptionPane.ERROR_MESSAGE);
        }
        
        return validation;
        
        
    }

    private void updateExistingTeam_ButtonActionPerformed(java.awt.event.ActionEvent evt)
    {//GEN-FIRST:event_updateExistingTeam_ButtonActionPerformed
        // TODO add your handling code here:
        if (validateExistingTeam() == true){
            // get updated team data string values
            String selectedTeam = ( String) updateTeam_ComboBox.getSelectedItem();
            String updateContactPerson = updateContactPerson_TextField.getText();
            String updateContactPhone = updateContactPhone_TextField.getText();
            String updateContactEmail = updateContactEmail_TextField.getText();
            
            int yesOrNo = JOptionPane.showConfirmDialog(null,
                    " You are about to update team for" + selectedTeam + "\n" +
            "Do you wish to proceed? Yes or No?", "Update team",
            JOptionPane.YES_NO_OPTION);
            
            
            // check if yes or no
            if  (yesOrNo == JOptionPane.YES_OPTION){
                int selectedIndex = -1;
                for (int i = 0; i < teamList.size(); i++) {
                    if (teamList.get(i).getTeamName().equals(selectedTeam)) {
                        selectedIndex = i;
                        break;
                    }
                }
            
                if (selectedIndex != -1) {
                    // Remove the old team
                    teamList.remove(selectedIndex);
                    
                    // Add a new team with updated information
                    teamList.add(new Team(selectedTeam, updateContactPerson, updateContactPhone, updateContactEmail));
                    
                    // Refresh the JComboBox
                    displayTeams();
                    
                    JOptionPane.showMessageDialog(null, "Team updated successfully!");  
                }
            }else{
                //no action
            }
        }          
        
    }//GEN-LAST:event_updateExistingTeam_ButtonActionPerformed

    
    /**************************************
     * Method:  Update Team_ComboBoxItemsChanged()
     * Purpose: Event Handler method for update team JComboBox option Change
     *          Displays team data for a Chosen team name via displayTeamDetails()
     *          method
     * Inputs:  ItemEvent evt (the event object passed into the itemStateChanged() method
     *          Although not used in this example, the evt object can provide data about the JComboBox
     * Outputs: Void
     *********************************/
    
    
    private void updateTeam_ComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_updateTeam_ComboBoxItemStateChanged
        // TODO add your handling code here:
    
        if ( comboBoxStatus == true)
        {
            displayTeamDetails();
        }
        
    }//GEN-LAST:event_updateTeam_ComboBoxItemStateChanged
/***********************************************************************
    Method: fromWindowCloosing()
    Purpose: Event handler method called whenever the app is closed
    *        (from clicking the "X" button at top right)
    Inputs:   WindowEvent evt ( the event object passsed into the formWindowClossing() method)
    *           Although not used in this example, the evt object can provide data about
    *           the windowEvent object
    Outputs : void
    
     **********************************************************************/
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        
         // yesor no variable for user's selection
    int yesOrNO = JOptionPane.showConfirmDialog(null, "Do you wish to save changes before closing?",
            "SAVE CHANGES?", JOptionPane.YES_NO_CANCEL_OPTION);
    if (yesOrNO == JOptionPane.YES_OPTION)
    {
        // Save competition data
        saveCompetitionData();
        // save team data
        saveTeamData();
    }
    else
    
    {
         // exit ---don't save changes    
    }
       
        
    }//GEN-LAST:event_formWindowClosing

    private void saveCompetitionData()
    {
        try
        { 
            FileWriter writer = new FileWriter("competitions.csv");
            
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            // 4. Loop through each line in the external file
            //     until end of file is reached (EOF)
            
            for (Competition competition : competitionList) {
                String line = competition.getGame() + "," + competition.getLocation() + "," + 
                competition.getCompetitionDate() + "," + competition.getTeam() + "," + competition.getPoints();

                bufferedWriter.write(line);

                bufferedWriter.newLine();
            }
            // 5 Close the writer object
            bufferedWriter.close();
            writer.close();
        
       
        }
        catch (IOException ioe)
        {
            // catch any file not found
            System.out.println("ERROR: write problem with competitions.csv file");
        }
    }
    
    private void saveTeamData()
    {
        try
        { 
            FileWriter writer = new FileWriter("teams.csv");
            
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            
            for (Team team : teamList) {
                String line = team.getTeamName() + "," + team.getContactName() + "," + 
                team.getContactPhone() + "," + team.getContactEmail();

                bufferedWriter.write(line);

                bufferedWriter.newLine();
            }
            // 5 Close the writer object
            bufferedWriter.close();
            writer.close();
       
        }
        catch (IOException ioe)
        {
            // catch any file not found
            System.out.println("ERROR: write problem with teams.csv file");
        } 
        
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GC_EGames_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GC_EGames_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GC_EGames_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GC_EGames_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GC_EGames_GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addDate_JLabel;
    private javax.swing.JLabel addGame_JLabel;
    private javax.swing.JLabel addNewCompPoints_JLabel;
    private javax.swing.JButton addNewCompResult_Button;
    private javax.swing.JPanel addNewCompResult_JPanel;
    private javax.swing.JLabel addNewCompTeam_JLabel;
    private javax.swing.JButton addNewTeam_Button;
    private javax.swing.JPanel addNewTeam_JPanel;
    private javax.swing.JLabel addResultTeam_JLabel;
    private javax.swing.JLabel addResultTitle_JLabel;
    private javax.swing.JTabbedPane body_JTabbedPane;
    private javax.swing.JPanel body_Panel;
    private javax.swing.JPanel compResult_JPanel;
    private javax.swing.JLabel compResults_JLabel;
    private javax.swing.JTable compResults_Table;
    private javax.swing.JButton displayTopTeams_Button;
    private javax.swing.JPanel header_Panel;
    private javax.swing.JLabel img_Label;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField newCompDate_TextField;
    private javax.swing.JTextField newCompGame_TextField;
    private javax.swing.JTextField newCompLocation_TextField;
    private javax.swing.JTextField newCompPoints_TextField;
    private javax.swing.JComboBox<String> newCompResult_ComboBox;
    private javax.swing.JTextField newContactEmail_TextField;
    private javax.swing.JLabel newContactPerson_JLabel1;
    private javax.swing.JTextField newContactPerson_TextField;
    private javax.swing.JTextField newContactPhone_TextField;
    private javax.swing.JLabel newTeamName_JLabel;
    private javax.swing.JTextField newTeamName_TextField;
    private javax.swing.JLabel titleAddNewteam_JPanel;
    private javax.swing.JLabel updateContactEmail_JLabel;
    private javax.swing.JTextField updateContactEmail_TextField;
    private javax.swing.JLabel updateContactPerson_JLabel;
    private javax.swing.JTextField updateContactPerson_TextField;
    private javax.swing.JLabel updateContactPhone_JLabel;
    private javax.swing.JTextField updateContactPhone_TextField;
    private javax.swing.JButton updateExistingTeam_Button;
    private javax.swing.JComboBox<String> updateTeam_ComboBox;
    private javax.swing.JPanel updateTeam_JPanel;
    // End of variables declaration//GEN-END:variables
}
