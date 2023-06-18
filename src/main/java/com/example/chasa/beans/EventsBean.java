package com.example.chasa.beans;

import com.example.chasa.entities.*;
import com.example.chasa.mail.Mail;
import com.example.chasa.mail.MailSender;
import com.example.chasa.services.*;
import com.example.chasa.utilities.EMF;
import com.example.chasa.utilities.FilterOfTable;
import com.example.chasa.utilities.ProcessUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ManagedBean
@SessionScoped
public class EventsBean extends FilterOfTable<EventsEntity> implements Serializable {
    private EventsEntity events = new EventsEntity();
    private EventsService eventsService = new EventsService();
    private String messageErrorDateTimeEvent = "hidden";
    private String messageErrorEventDiveSite = "hidden";
    private String messageErrorEventDiveSiteselect = "hidden";
    @Inject
    private ConnectionBean connectionBean;
    @Inject
    private AddressesBean addressesBean;
    @Inject
    private DiveSitesBean diveSitesBean;

    public void ResearchFilter(){

        EntityManager em = EMF.getEM();
        try{
            filterOfTable = eventsService.findEventsByFilter(this.filter, em);
            ProcessUtils.debug(this.filter);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
    }

    public void ResearchFilterMyPotentialEvents(){

        EntityManager em = EMF.getEM();
        try{
            filterOfTable = eventsService.findEventsByFilterForUser(this.filter,connectionBean.getUser().getIdUser(), em);
            ProcessUtils.debug(this.filter);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
    }

    public String submitFormAddEvents(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EventsService eventsService = new EventsService();
        AddressService addressService = new AddressService();
        UsersService usersService = new UsersService();
        EntityTransaction transaction = em.getTransaction();
        List<String> listEMail= new ArrayList<String>();
        Mail email = new Mail();
        Document doc = new Document();
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        LocalDate now = LocalDate.now();
        String dateEventPattern = "ddMMyyyy-HHmmss";
        String isoDatePattern = "yyyy-MM-dd";
        String isoTimePattern = "HH:mm";
        SimpleDateFormat dateEventFormat = new SimpleDateFormat(dateEventPattern);
        String dateTimeEvent = dateEventFormat.format(events.getDateTimeEvent());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String dateEvent = simpleDateFormat.format(events.getDateTimeEvent());
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(isoTimePattern);
        String dateTime = simpleTimeFormat.format(events.getDateTimeEvent());
        int resultNow = String.valueOf(now).compareTo(dateEvent);
        ProcessUtils.debug("Ctrl date " + resultNow);
        ProcessUtils.debug("Ctrl date " + now);
        ProcessUtils.debug("Ctrl date " + dateEvent);

        String filename;
        String source;

        if(resultNow >= 1){
            this.messageErrorDateTimeEvent = "";
            this.messageErrorEventDiveSite = "hidden";
            this.messageErrorEventDiveSiteselect = "hidden";
            redirect = "null";
            return redirect;
        }else if (events.getEventCategoriesByIdEventCategory().getEventCategoryLabel().equalsIgnoreCase("Plongée de nuit") && events.getDiveSitesByIdDiveSite()== null) {
            this.messageErrorDateTimeEvent = "hidden";
            this.messageErrorEventDiveSite = "";
            this.messageErrorEventDiveSiteselect = "hidden";
            redirect = "null";
            return redirect;
        }else if (events.getEventCategoriesByIdEventCategory().getEventCategoryLabel().equalsIgnoreCase("Sortie club") && events.getDiveSitesByIdDiveSite()== null) {
            this.messageErrorDateTimeEvent = "hidden";
            this.messageErrorEventDiveSite = "";
            this.messageErrorEventDiveSiteselect = "hidden";
            redirect = "null";
            return redirect;
        } else if (events.getEventCategoriesByIdEventCategory().getEventCategoryLabel().equalsIgnoreCase("Barbecue") && events.getDiveSitesByIdDiveSite()!= null) {
            this.messageErrorDateTimeEvent = "hidden";
            this.messageErrorEventDiveSite = "hidden";
            this.messageErrorEventDiveSiteselect = "";
            redirect = "null";
            return redirect;
        }else if (events.getEventCategoriesByIdEventCategory().getEventCategoryLabel().equalsIgnoreCase("Assemblée générale") && events.getDiveSitesByIdDiveSite()!= null) {
            this.messageErrorDateTimeEvent = "hidden";
            this.messageErrorEventDiveSite = "hidden";
            this.messageErrorEventDiveSiteselect = "";
            redirect = "null";
            return redirect;
        }else{
            try{
                transaction.begin();
                eventsService.addEvent(events,em);
                transaction.commit();
                try{
                    //ProcessUtils.debug(""+usersService.findAll(em).size());
                    listEMail = usersService.findAll(em).stream().map(UsersEntity::getEmail).collect(Collectors.toList());

                }catch (Exception e) {
                    e.printStackTrace();
                }
                confirmAdd();
            }catch(Exception e){
                redirect = "null";
            }finally{
                if(transaction.isActive())
                {
                    transaction.rollback();
                }
                em.close();
            }
            List <DiveSiteCharacteristicsEntity>characteristicDive = new ArrayList();
            if(events.getDiveSitesByIdDiveSite() != null){
                characteristicDive = events.getDiveSitesByIdDiveSite().getListOfDiveSiteCharacteristics();
            }

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
            LocalDateTime nowMail = LocalDateTime.now();
            filename = "Reservation" + "_" +events.getEventCategoriesByIdEventCategory().getEventCategoryLabel()+dateTimeEvent +dtf.format(nowMail)+ ".pdf";
            source = "C:\\Users\\devog\\IdeaProjects\\chasa\\src\\main\\webapp\\PDF\\" + filename;
            try {

                PdfWriter.getInstance(doc, new FileOutputStream(source));
                doc.open();
                Paragraph p = new Paragraph();
                Paragraph p1 = new Paragraph();
                String summary = bundle.getString("summary");
                String category = bundle.getString("category.event");
                String addressmeet = bundle.getString("addressmeet");
                String price = bundle.getString("price.event");
                String at = bundle.getString("at");
                String maxNumPerson = bundle.getString("maxNumPeople.event");
                String characteristic = bundle.getString("characteristic.diveSite");
                String addressDiveSite = bundle.getString("addressDiveSite");
                String addressHyperbare = bundle.getString("addressHyperbare");
                String hyperbareName = bundle.getString("hyperbareName");
                String reminder = bundle.getString("reminder");
                String bubbles = bundle.getString("bubbles");
                String myP="";
                Font f = new Font();
                f.isBold();
                f.setSize(16);
                f.setStyle(Paragraph.ALIGN_MIDDLE);
                f.isUnderlined();
                doc.add(new Paragraph(summary+ dateEvent + " " + at + " " + dateTime, f));
                p.add(category+" : " + events.getEventCategoriesByIdEventCategory().getEventCategoryLabel()
                        +"\n "+ addressmeet+" : " + events.getAddressesByIdAddress().getStreet()+" "+ events.getAddressesByIdAddress().getNumber()+ " "+ events.getAddressesByIdAddress().getBox()+" "+events.getAddressesByIdAddress().getIdCity().getPostalCode()+" "+ events.getAddressesByIdAddress().getIdCity().getCityLabel()
                        +"\n "+ price+" : " + events.getPrice()+"€"
                        +"\n "+ maxNumPerson+" : "+events.getMaxNumPeople() );
                doc.add(p);
                if(Objects.equals(events.getEventCategoriesByIdEventCategory().getEventCategoryLabel(), "Barbecue")){
                    Image image = Image.getInstance("C:\\Users\\devog\\IdeaProjects\\chasa\\src\\main\\webapp\\CSS\\Pictures\\barbecue.jpg");
                    image.scaleAbsolute(400f, 400f);
                    doc.add(image);
                }
                else if(Objects.equals(events.getEventCategoriesByIdEventCategory().getEventCategoryLabel(), "Assemblée générale")){
                    Image image = Image.getInstance("C:\\Users\\devog\\IdeaProjects\\chasa\\src\\main\\webapp\\CSS\\Pictures\\Assemblée générale.jpg");
                    image.scaleAbsolute(400f, 400f);
                    doc.add(image);
                }
                else if(events.getDiveSitesByIdDiveSite().getIdDiveSite()== 1){
                    Image image = Image.getInstance("C:\\Users\\devog\\IdeaProjects\\chasa\\src\\main\\webapp\\CSS\\Pictures\\Barges.jpg");
                    image.scaleAbsolute(550f, 400f);
                    doc.add(new Paragraph(characteristic+" :"+ events.getDiveSitesByIdDiveSite().getDiveSiteLabel(),f));
                    doc.add(image);

                    p1.add( addressDiveSite+" : " + events.getDiveSitesByIdDiveSite().getAddressesByIdAddress().getStreet()+" "+ events.getAddressesByIdAddress().getNumber()+ " "+ events.getAddressesByIdAddress().getBox()+" "+events.getAddressesByIdAddress().getIdCity().getPostalCode()+" "+ events.getAddressesByIdAddress().getIdCity().getCityLabel()
                            +"\n"+hyperbareName+" : "+ events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getHyperbaricChamberLabel()
                            +"\n"+addressHyperbare+" : "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getStreet()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getNumber()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getIdCity().getCityLabel());
                    for(int i=0; i<characteristicDive.size(); i++)
                        myP += ("\n"+characteristicDive.get(i).getCharacteristicsByIdCharacteristic().getCharacteristicLabel()+" : "+characteristicDive.get(i).getValue());
                    p1.add(myP);
                    doc.add(p1);
                }
                else if(events.getDiveSitesByIdDiveSite().getIdDiveSite()== 2){
                    Image image = Image.getInstance("C:\\Users\\devog\\IdeaProjects\\chasa\\src\\main\\webapp\\CSS\\Pictures\\Vodelée.jpg");
                    image.scaleAbsolute(550f, 400f);

                    doc.add(new Paragraph(characteristic+" :"+ events.getDiveSitesByIdDiveSite().getDiveSiteLabel(),f));
                    doc.add(image);
                    p1.add(addressDiveSite+" : " + events.getDiveSitesByIdDiveSite().getAddressesByIdAddress().getStreet()+" "+ events.getAddressesByIdAddress().getNumber()+ " "+ events.getAddressesByIdAddress().getBox()+" "+events.getAddressesByIdAddress().getIdCity().getPostalCode()+" "+ events.getAddressesByIdAddress().getIdCity().getCityLabel()
                            +"\n"+hyperbareName+" : "+ events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getHyperbaricChamberLabel()
                            +"\n"+addressHyperbare+" : "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getStreet()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getNumber()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getIdCity().getCityLabel());
                    for(int i=0; i<characteristicDive.size(); i++)
                        myP += ("\n"+characteristicDive.get(i).getCharacteristicsByIdCharacteristic().getCharacteristicLabel()+" : "+characteristicDive.get(i).getValue());
                    p1.add(myP);
                    doc.add(p1);
                }
                else if(events.getDiveSitesByIdDiveSite().getIdDiveSite()== 3){
                    Image image = Image.getInstance("C:\\Users\\devog\\IdeaProjects\\chasa\\src\\main\\webapp\\CSS\\Pictures\\Vodecée.jpg");
                    image.scaleAbsolute(550f, 400f);

                    doc.add(new Paragraph(characteristic+" :"+ events.getDiveSitesByIdDiveSite().getDiveSiteLabel(),f));
                    doc.add(image);
                    p1.add(addressDiveSite+" : " + events.getDiveSitesByIdDiveSite().getAddressesByIdAddress().getStreet()+" "+ events.getAddressesByIdAddress().getNumber()+ " "+ events.getAddressesByIdAddress().getBox()+" "+events.getAddressesByIdAddress().getIdCity().getPostalCode()+" "+ events.getAddressesByIdAddress().getIdCity().getCityLabel()
                            +"\n"+hyperbareName+" : "+ events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getHyperbaricChamberLabel()
                            +"\n"+addressHyperbare+" : "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getStreet()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getNumber()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getIdCity().getCityLabel());
                    for(int i=0; i<characteristicDive.size(); i++)
                        myP += ("\n"+characteristicDive.get(i).getCharacteristicsByIdCharacteristic().getCharacteristicLabel()+" : "+characteristicDive.get(i).getValue());
                    p1.add(myP);
                    doc.add(p1);
                }
                else if(events.getDiveSitesByIdDiveSite().getIdDiveSite()== 4){
                    Image image = Image.getInstance("C:\\Users\\devog\\IdeaProjects\\chasa\\src\\main\\webapp\\CSS\\Pictures\\Pate taille.jpg");
                    image.scaleAbsolute(550f, 400f);

                    doc.add(new Paragraph(characteristic+" :"+ events.getDiveSitesByIdDiveSite().getDiveSiteLabel(),f));
                    doc.add(image);
                    p1.add(addressDiveSite+" : " + events.getDiveSitesByIdDiveSite().getAddressesByIdAddress().getStreet()+" "+ events.getAddressesByIdAddress().getNumber()+ " "+ events.getAddressesByIdAddress().getBox()+" "+events.getAddressesByIdAddress().getIdCity().getPostalCode()+" "+ events.getAddressesByIdAddress().getIdCity().getCityLabel()
                            +"\n"+hyperbareName+" : "+ events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getHyperbaricChamberLabel()
                            +"\n"+addressHyperbare+" : "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getStreet()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getNumber()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getIdCity().getCityLabel());
                    for(int i=0; i<characteristicDive.size(); i++)
                        myP += ("\n"+characteristicDive.get(i).getCharacteristicsByIdCharacteristic().getCharacteristicLabel()+" : "+characteristicDive.get(i).getValue());
                    p1.add(myP);
                    doc.add(p1);
                }
                Font f1 = new Font();
                f1.setColor(BaseColor.RED);
                f1.setStyle(Font.BOLD);
                doc.add(new Paragraph("\n"+ reminder, f1));
                doc.add(new Paragraph("\n"+ bubbles));

                doc.close();

            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        }

        String membre = bundle.getString("membre");
        String at = bundle.getString("at");
        String subject = bundle.getString("subject");
        email.setFrom("teamchasa@outlook.com");
        email.setMsgBody(membre + " " + events.getEventCategoriesByIdEventCategory().getEventCategoryLabel() + " " + dateEvent + " " + at + " " + dateTime);
        email.setSubject(subject + " " + events.getEventCategoriesByIdEventCategory().getEventCategoryLabel() + " " + dateEvent + " " + at + " " + dateTime);
        email.setNick("Chasa");
        email.setReplyTo("teamchasa@outlook.com");
        email.setListTo(listEMail);
        //ProcessUtils.debug(""+listEMail.size());
        email.getListTo().add("thomas.devogelaere@promsocatc.net");
        //ProcessUtils.debug(""+email.getListTo().size());
        email.setEncodeUTF8(true);
        email.setFilename(filename);
        email.setSource(source);
        try {
            MailSender.sendMail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
            initFormEvent();
            return  redirect;

    }

    public String submitFormUpdateEvents() {
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EventsService eventsService = new EventsService();
        DiveSiteCharacteristicService diveSiteCharacteristicService = new DiveSiteCharacteristicService();
        UsersService usersService = new UsersService();
        EntityTransaction transaction = em.getTransaction();
        List<String> listEMail= new ArrayList<String>();
        Mail email = new Mail();
        Document doc = new Document();
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        LocalDate now = LocalDate.now();
        String dateEventPattern = "ddMMyyyy-HHmmss";
        String isoDatePattern = "yyyy-MM-dd";
        String isoTimePattern = "HH:mm";
        SimpleDateFormat dateEventFormat = new SimpleDateFormat(dateEventPattern);
        String dateTimeEvent = dateEventFormat.format(events.getDateTimeEvent());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String dateEvent = simpleDateFormat.format(events.getDateTimeEvent());
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(isoTimePattern);
        String dateTime = simpleTimeFormat.format(events.getDateTimeEvent());
        int resultNow = String.valueOf(now).compareTo(dateEvent);
        //ProcessUtils.debug("Ctrl date " + resultNow);
        String myP="";

        String filename;
        String source;
        if (resultNow >= 1) {
            this.messageErrorDateTimeEvent = "";
            this.messageErrorEventDiveSite = "hidden";
            this.messageErrorEventDiveSiteselect = "hidden";
            redirect = "null";
            return redirect;
        }else if (events.getEventCategoriesByIdEventCategory().getEventCategoryLabel().equalsIgnoreCase("Plongée de nuit") && events.getDiveSitesByIdDiveSite()== null) {
            this.messageErrorDateTimeEvent = "hidden";
            this.messageErrorEventDiveSite = "";
            this.messageErrorEventDiveSiteselect = "hidden";
            redirect = "null";
            return redirect;
        }else if (events.getEventCategoriesByIdEventCategory().getEventCategoryLabel().equalsIgnoreCase("Sortie club") && events.getDiveSitesByIdDiveSite()== null) {
            this.messageErrorDateTimeEvent = "hidden";
            this.messageErrorEventDiveSite = "";
            this.messageErrorEventDiveSiteselect = "hidden";
            redirect = "null";
            return redirect;
        } else if (events.getEventCategoriesByIdEventCategory().getEventCategoryLabel().equalsIgnoreCase("Barbecue") && events.getDiveSitesByIdDiveSite()!= null) {
            this.messageErrorDateTimeEvent = "hidden";
            this.messageErrorEventDiveSite = "hidden";
            this.messageErrorEventDiveSiteselect = "";
            redirect = "null";
            return redirect;
        }else if (events.getEventCategoriesByIdEventCategory().getEventCategoryLabel().equalsIgnoreCase("Assemblée générale") && events.getDiveSitesByIdDiveSite()!= null) {
            this.messageErrorDateTimeEvent = "hidden";
            this.messageErrorEventDiveSite = "hidden";
            this.messageErrorEventDiveSiteselect = "";
            redirect = "null";
            return redirect;
        }else {
            try {
                transaction.begin();
                eventsService.updateEvent(events, em);
                transaction.commit();
                try{
                    ProcessUtils.debug(""+usersService.findAll(em).size());
                    listEMail = usersService.findAll(em).stream().map(UsersEntity::getEmail).collect(Collectors.toList());

                }catch (Exception e) {
                    e.printStackTrace();
                }
                confirmUpdate();

            } catch (Exception e) {
                redirect = "null";
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                em.close();
            }
            List <DiveSiteCharacteristicsEntity>characteristicDive = new ArrayList();
            if(events.getDiveSitesByIdDiveSite() != null){
                characteristicDive = events.getDiveSitesByIdDiveSite().getListOfDiveSiteCharacteristics();
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
            LocalDateTime nowMail = LocalDateTime.now();
            filename = "Reservation" + "_" +events.getEventCategoriesByIdEventCategory().getEventCategoryLabel()+dateTimeEvent +dtf.format(nowMail)+ ".pdf";
            source = "C:\\Users\\devog\\IdeaProjects\\chasa\\src\\main\\webapp\\PDF\\" + filename;
            try {

                PdfWriter.getInstance(doc, new FileOutputStream(source));
                doc.open();
                Paragraph p = new Paragraph();
                Paragraph p1 = new Paragraph();


                String summary = bundle.getString("summary");
                String category = bundle.getString("category.event");
                String addressmeet = bundle.getString("addressmeet");
                String price = bundle.getString("price.event");
                String at = bundle.getString("at");
                String maxNumPerson = bundle.getString("maxNumPeople.event");
                String characteristic = bundle.getString("characteristic.diveSite");
                String addressDiveSite = bundle.getString("addressDiveSite");
                String addressHyperbare = bundle.getString("addressHyperbare");
                String hyperbareName = bundle.getString("hyperbareName");
                String reminder = bundle.getString("reminder");
                String bubbles = bundle.getString("bubbles");
                Font f = new Font();
                f.isBold();
                f.setSize(16);
                f.setStyle(Paragraph.ALIGN_MIDDLE);
                f.isUnderlined();
                doc.add(new Paragraph(summary+ dateEvent + " " + at + " " + dateTime, f));
                p.add( category+" : " + events.getEventCategoriesByIdEventCategory().getEventCategoryLabel()
                     +"\n "+ addressmeet+" : " + events.getAddressesByIdAddress().getStreet()+" "+ events.getAddressesByIdAddress().getNumber()+ " "+ events.getAddressesByIdAddress().getBox()+" "+events.getAddressesByIdAddress().getIdCity().getPostalCode()+" "+ events.getAddressesByIdAddress().getIdCity().getCityLabel()
                     +"\n "+ price+" : " + events.getPrice()+"€"
                     +"\n "+ maxNumPerson+" : "+events.getMaxNumPeople() );
                doc.add(p);
                if(Objects.equals(events.getEventCategoriesByIdEventCategory().getEventCategoryLabel(), "Barbecue")){
                    Image image = Image.getInstance("C:\\Users\\devog\\IdeaProjects\\chasa\\src\\main\\webapp\\CSS\\Pictures\\barbecue.jpg");
                    image.scaleAbsolute(400f, 400f);
                    doc.add(image);
                }
                else if(Objects.equals(events.getEventCategoriesByIdEventCategory().getEventCategoryLabel(), "Assemblée générale")){
                    Image image = Image.getInstance("C:\\Users\\devog\\IdeaProjects\\chasa\\src\\main\\webapp\\CSS\\Pictures\\Assemblée générale.jpg");
                    image.scaleAbsolute(400f, 400f);
                    doc.add(image);
                }
                else if(events.getDiveSitesByIdDiveSite().getIdDiveSite()== 1){
                    Image image = Image.getInstance("C:\\Users\\devog\\IdeaProjects\\chasa\\src\\main\\webapp\\CSS\\Pictures\\Barges.jpg");
                    image.scaleAbsolute(550f, 400f);
                    doc.add(new Paragraph(characteristic+" :"+ events.getDiveSitesByIdDiveSite().getDiveSiteLabel(),f));
                    doc.add(image);

                    p1.add( addressDiveSite+" : " + events.getDiveSitesByIdDiveSite().getAddressesByIdAddress().getStreet()+" "+ events.getAddressesByIdAddress().getNumber()+ " "+ events.getAddressesByIdAddress().getBox()+" "+events.getAddressesByIdAddress().getIdCity().getPostalCode()+" "+ events.getAddressesByIdAddress().getIdCity().getCityLabel()
                           +"\n"+hyperbareName+" : "+ events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getHyperbaricChamberLabel()
                           +"\n"+addressHyperbare+" : "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getStreet()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getNumber()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getIdCity().getCityLabel());
                    for(int i=0; i<characteristicDive.size(); i++)
                        myP += ("\n"+characteristicDive.get(i).getCharacteristicsByIdCharacteristic().getCharacteristicLabel()+" : "+characteristicDive.get(i).getValue());
                    p1.add(myP);
                    doc.add(p1);
                }
                else if(events.getDiveSitesByIdDiveSite().getIdDiveSite()== 2){
                    Image image = Image.getInstance("C:\\Users\\devog\\IdeaProjects\\chasa\\src\\main\\webapp\\CSS\\Pictures\\Vodelée.jpg");
                    image.scaleAbsolute(550f, 400f);

                    doc.add(new Paragraph(characteristic+" :"+ events.getDiveSitesByIdDiveSite().getDiveSiteLabel(),f));
                    doc.add(image);
                    p1.add( addressDiveSite+" : " + events.getDiveSitesByIdDiveSite().getAddressesByIdAddress().getStreet()+" "+ events.getAddressesByIdAddress().getNumber()+ " "+ events.getAddressesByIdAddress().getBox()+" "+events.getAddressesByIdAddress().getIdCity().getPostalCode()+" "+ events.getAddressesByIdAddress().getIdCity().getCityLabel()
                            +"\n"+hyperbareName+" : "+ events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getHyperbaricChamberLabel()
                            +"\n"+addressHyperbare+" : "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getStreet()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getNumber()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getIdCity().getCityLabel());
                    for(int i=0; i<characteristicDive.size(); i++)
                        myP += ("\n"+characteristicDive.get(i).getCharacteristicsByIdCharacteristic().getCharacteristicLabel()+" : "+characteristicDive.get(i).getValue());
                    p1.add(myP);
                    doc.add(p1);
                }
                else if(events.getDiveSitesByIdDiveSite().getIdDiveSite()== 3){
                    Image image = Image.getInstance("C:\\Users\\devog\\IdeaProjects\\chasa\\src\\main\\webapp\\CSS\\Pictures\\Vodecée.jpg");
                    image.scaleAbsolute(550f, 400f);

                    doc.add(new Paragraph(characteristic+" :"+ events.getDiveSitesByIdDiveSite().getDiveSiteLabel(),f));
                    doc.add(image);
                    p1.add( addressDiveSite+" : " + events.getDiveSitesByIdDiveSite().getAddressesByIdAddress().getStreet()+" "+ events.getAddressesByIdAddress().getNumber()+ " "+ events.getAddressesByIdAddress().getBox()+" "+events.getAddressesByIdAddress().getIdCity().getPostalCode()+" "+ events.getAddressesByIdAddress().getIdCity().getCityLabel()
                            +"\n"+hyperbareName+" : "+ events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getHyperbaricChamberLabel()
                            +"\n"+addressHyperbare+" : "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getStreet()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getNumber()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getIdCity().getCityLabel());
                    for(int i=0; i<characteristicDive.size(); i++)
                        myP += ("\n"+characteristicDive.get(i).getCharacteristicsByIdCharacteristic().getCharacteristicLabel()+" : "+characteristicDive.get(i).getValue());
                    p1.add(myP);
                    doc.add(p1);
                }
                else if(events.getDiveSitesByIdDiveSite().getIdDiveSite()== 4){
                    Image image = Image.getInstance("C:\\Users\\devog\\IdeaProjects\\chasa\\src\\main\\webapp\\CSS\\Pictures\\Pate taille.jpg");
                    image.scaleAbsolute(550f, 400f);

                    doc.add(new Paragraph(characteristic+" :"+ events.getDiveSitesByIdDiveSite().getDiveSiteLabel(),f));
                    doc.add(image);
                    p1.add(addressDiveSite+" : " + events.getDiveSitesByIdDiveSite().getAddressesByIdAddress().getStreet()+" "+ events.getAddressesByIdAddress().getNumber()+ " "+ events.getAddressesByIdAddress().getBox()+" "+events.getAddressesByIdAddress().getIdCity().getPostalCode()+" "+ events.getAddressesByIdAddress().getIdCity().getCityLabel()
                            +"\n"+hyperbareName+" : "+ events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getHyperbaricChamberLabel()
                            +"\n"+addressHyperbare+" : "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getStreet()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getNumber()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getIdCity().getCityLabel());
                    for(int i=0; i<characteristicDive.size(); i++)
                        myP += ("\n"+characteristicDive.get(i).getCharacteristicsByIdCharacteristic().getCharacteristicLabel()+" : "+characteristicDive.get(i).getValue());
                    p1.add(myP);
                    doc.add(p1);
                }
                Font f1 = new Font();
                f1.setColor(BaseColor.RED);
                f1.setStyle(Font.BOLD);
                doc.add(new Paragraph("\n"+ reminder, f1));
                doc.add(new Paragraph("\n"+ bubbles));

                doc.close();

            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        }
        String membreupdate = bundle.getString("membreupdate");
        String at = bundle.getString("at");
        String subject = bundle.getString("subject");
        email.setFrom("teamchasa@outlook.com");
        email.setMsgBody(membreupdate + " " + events.getEventCategoriesByIdEventCategory().getEventCategoryLabel() + " " + dateEvent + " " + at + " " + dateTime);
        email.setSubject(subject + " " + events.getEventCategoriesByIdEventCategory().getEventCategoryLabel() + " " + dateEvent + " " + at + " " + dateTime);
        email.setNick("Chasa");
        email.setReplyTo("teamchasa@outlook.com");
        email.setListTo(listEMail);
        email.getListTo().add("thomas.devogelaere@promsocatc.net");
        email.setEncodeUTF8(true);
        email.setFilename(filename);
        email.setSource(source);
        try {
            MailSender.sendMail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initFormEvent();
        return redirect;
    }





    public void initFormEvent(){
        this.events.setEventCategoriesByIdEventCategory(new EventCategoriesEntity());
        this.events.setDateTimeEvent(new Date());
        this.events.setMaxNumPeople(0);
        this.events.setPrice(0.00);
        this.events.setAddressesByIdAddress(new AddressesEntity());
        this.events.setLicensesByIdLicense(new LicensesEntity());
        this.events.setDiveSitesByIdDiveSite(new DiveSitesEntity());
        this.messageErrorDateTimeEvent = "hidden";
        this.messageErrorEventDiveSite = "hidden";
        this.messageErrorEventDiveSiteselect = "hidden";
    }

    public String cancelForm(){
        String redirect = "/VIEW/home";
        initFormEvent();
        return redirect;
    }

    public void confirmAdd() {
        String isoDatePattern = "dd/MM/yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String dateEvent = simpleDateFormat.format(events.getDateTimeEvent());
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        //ProcessUtils.debug(""+ bundle);
        String pageTitle = bundle.getString("Event");
        String pageTitle2 = bundle.getString("category");
        String pageTitle3 = bundle.getString("add");
        addMessage(pageTitle +" "+dateEvent+" "+ pageTitle2+" "+events.getEventCategoriesByIdEventCategory().getEventCategoryLabel()+" "+pageTitle3,"Confirmation");
    }

    public void confirmUpdate() {
        String isoDatePattern = "dd/MM/yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String dateEvent = simpleDateFormat.format(events.getDateTimeEvent());
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        //ProcessUtils.debug(""+ bundle);
        String pageTitle = bundle.getString("Event");
        String pageTitle2 = bundle.getString("category");
        String pageTitle3 = bundle.getString("update");
        addMessage(pageTitle + " "+dateEvent+" "+ pageTitle2+" "+events.getEventCategoriesByIdEventCategory().getEventCategoryLabel()+" "+pageTitle3,"Confirmation");
    }

    public void confirmDelete() {
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        //ProcessUtils.debug(""+ bundle);
        String pageTitle = bundle.getString("event.Event");
        String pageTitle3 = bundle.getString("delete");
        addMessage(pageTitle+" "+ events.getDateTimeEvent()+ " "+ pageTitle3 ,"Confirmation");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /*---Getters and setters---*/

    public EventsEntity getEvents() {
        return events;
    }

    public void setEvents(EventsEntity events) {
        this.events = events;
    }

    public EventsService getEventsService() {
        return eventsService;
    }

    public void setEventsService(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    public String getMessageErrorDateTimeEvent() {
        return messageErrorDateTimeEvent;
    }

    public void setMessageErrorDateTimeEvent(String messageErrorDateTimeEvent) {
        this.messageErrorDateTimeEvent = messageErrorDateTimeEvent;
    }

    public String getMessageErrorEventDiveSite() {
        return messageErrorEventDiveSite;
    }

    public void setMessageErrorEventDiveSite(String messageErrorEventDiveSite) {
        this.messageErrorEventDiveSite = messageErrorEventDiveSite;
    }
    public ConnectionBean getConnectionBean() {
        return connectionBean;
    }

    public void setConnectionBean(ConnectionBean connectionBean) {
        this.connectionBean = connectionBean;
    }

    public AddressesBean getAddressesBean() {
        return addressesBean;
    }

    public void setAddressesBean(AddressesBean addressesBean) {
        this.addressesBean = addressesBean;
    }

    public DiveSitesBean getDiveSitesBean() {
        return diveSitesBean;
    }

    public void setDiveSitesBean(DiveSitesBean diveSitesBean) {
        this.diveSitesBean = diveSitesBean;
    }

    public String getMessageErrorEventDiveSiteselect() {
        return messageErrorEventDiveSiteselect;
    }

    public void setMessageErrorEventDiveSiteselect(String messageErrorEventDiveSiteselect) {
        this.messageErrorEventDiveSiteselect = messageErrorEventDiveSiteselect;
    }
}
