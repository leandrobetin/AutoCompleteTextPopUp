/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seu.pacote.aqui

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author leandro
 */
public class AutoCompleteTextPopUp {
    
    private JTextField txtComponent;
    private JWindow windowMain;
    private JDialog dialogBase;
    private JPanel panelMain;
      
    private JScrollPane scrollPane;
       
    private ArrayList<String> words;
    
    private int totalHeight = 0;
    
    private String textoDigitado = "";
    
    private MouseListener listenerMouseClick;
    
    private Point p;
    
    private ArrayList<JLabel> wordsListLabels;
    
    private int indexWordSelecionado = -1;
    
    private Font font = new Font("Arial", Font.PLAIN, 12);
    private Font font_ = new Font("Arial", Font.BOLD, 14);
    private Color row_hover_color = new Color(243,243,243);
    
    private EmptyBorder labelBorderPadding = new EmptyBorder(0,10,0,0);
   
    public AutoCompleteTextPopUp() {
    }

    public AutoCompleteTextPopUp(final JTextField txtComponent, JDialog dialogBase, ArrayList<String> words) {
        this.txtComponent = txtComponent;
        this.dialogBase = dialogBase;
        this.words = words;
        try{
               
            criarWindowPopUp();
            
            criarEventosTecladoTextField();
                        
            listenerMouseClick = new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent me) {   }

                @Override
                public void mousePressed(MouseEvent me) { }

                @Override
                public void mouseReleased(MouseEvent me) {
                   
                    if(me.getSource() instanceof JLabel){
                        selecionarTexto(((JLabel) me.getSource()).getText());
                    }                       
                }

                @Override
                public void mouseEntered(MouseEvent me) {
                    if(me.getSource() instanceof JLabel){
                        ((JLabel) me.getSource()).setFont(font_);
                        ((JLabel) me.getSource()).setBackground(row_hover_color);
                    } 
                }

                @Override
                public void mouseExited(MouseEvent me) {
                    if(me.getSource() instanceof JLabel){
                        ((JLabel) me.getSource()).setFont(font);
                        ((JLabel) me.getSource()).setBackground(Color.WHITE);
                    } 
                }
            };
           
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    
    private void criarWindowPopUp() throws Exception{
       
        windowMain = new JWindow(dialogBase);
        
        panelMain = new JPanel();
        panelMain.setLayout(new GridLayout(0, 1));
        panelMain.setBackground(Color.WHITE);
        scrollPane = new JScrollPane(panelMain);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);        
       
        
        windowMain.getContentPane().add(scrollPane);
       
        windowMain.setSize(txtComponent.getWidth(), totalHeight);
        
        windowMain.setVisible(true);  
        
        windowMain.setMinimumSize(new Dimension(txtComponent.getWidth(), 0));
       
        windowMain.setOpacity(0.95f);
        
        windowMain.revalidate();
        windowMain.repaint();
        
        windowMain.setVisible(false);  //somente cria a janela...ainda nao mostra ela...será exibida somente quando digitar algo
        
       
    }
        
    private void criarEventosTecladoTextField() throws Exception{
        
        txtComponent.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
             
            }

            @Override
            public void keyPressed(KeyEvent ke) {
               
            }

            @Override
            public void keyReleased(KeyEvent ke) {
               
                textoDigitado = txtComponent.getText().toUpperCase();
                if(textoDigitado.length() <= 2){ windowMain.setVisible(false); return;}
                
                if(ke.getKeyCode() == KeyEvent.VK_DOWN){
                    
                    indexWordSelecionado++;
                    destacarWordNaLista();
                }
                else{
                    if(ke.getKeyCode() == KeyEvent.VK_UP){
                        
                        indexWordSelecionado--;
                        destacarWordNaLista();
                        
                    }else{
                       
                        if(ke.getKeyCode() == KeyEvent.VK_ENTER){
                            selecionarTexto();
                        }else{
                           checkMatches(); 
                        }
                            
                    }
                        
                }
               
            }
        });
        
    }
    
    private void checkMatches(){
        
        try{
            
            // mostrar a janela e posicionar
            p = txtComponent.getLocationOnScreen();        
            windowMain.setLocation(p.x, p.y + txtComponent.getHeight());
            windowMain.setVisible(true);
            
            //limpar o painel
            panelMain.removeAll();
            panelMain.repaint();
            panelMain.revalidate();
            
            totalHeight = 0;
               
            int matches = 0;
            
            wordsListLabels = new ArrayList<>();
            
            indexWordSelecionado = -1;
                        
            for(String word: words){
                
                if((word.toUpperCase().contains(textoDigitado))){//verificar se da 'match' :p
                    
                    JLabel label = new javax.swing.JLabel(word.toUpperCase());
                    label.addMouseListener(listenerMouseClick);    
                    label.setFont(font);
                    label.setOpaque(true);
                    label.setBackground(Color.WHITE);
                    label.setBorder(labelBorderPadding);
                    panelMain.add(label);
                    totalHeight += txtComponent.getHeight();
                    matches ++;
                    
                    
                    wordsListLabels.add(label);
                }
            }
            
           
            panelMain.repaint();
            panelMain.revalidate();
            
            windowMain.setSize(txtComponent.getWidth(), txtComponent.getHeight() * matches);
            windowMain.repaint();
            windowMain.revalidate();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void selecionarTexto(){
        txtComponent.setText( wordsListLabels.get(indexWordSelecionado).getText().toUpperCase());
        windowMain.setVisible(false);
    }
    
    private void selecionarTexto(String texto){
        txtComponent.setText(texto.toUpperCase());
        windowMain.setVisible(false);
    }
    
    private void destacarWordNaLista(){
        
        try{
            
            for (JLabel lbl : wordsListLabels) {
                lbl.setFont(font);
                lbl.setBackground(Color.WHITE);
            }
            
            wordsListLabels.get(indexWordSelecionado).setFont(font_);
            wordsListLabels.get(indexWordSelecionado).setBackground(row_hover_color);
            
        }catch(Exception e){ indexWordSelecionado = -1; }
        
    }
     
    
    
    
}
