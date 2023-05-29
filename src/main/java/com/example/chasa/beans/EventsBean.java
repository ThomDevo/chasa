package com.example.chasa.beans;

import com.example.chasa.entities.*;
import com.example.chasa.mail.Mail;
import com.example.chasa.mail.MailSender;
import com.example.chasa.services.AddressService;
import com.example.chasa.services.DiveSiteCharacteristicService;
import com.example.chasa.services.EventsService;
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
    private List<DiveSiteCharacteristicsEntity> allCharacteristics;
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

    public String submitFormAddEvents(AddressesEntity address){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EventsService eventsService = new EventsService();
        AddressService addressService = new AddressService();
        EntityTransaction transaction = em.getTransaction();
        LocalDate now = LocalDate.now();
        String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String dateEvent = simpleDateFormat.format(events.getDateTimeEvent());
        int resultNow = dateEvent.compareTo(String.valueOf(now));
        ProcessUtils.debug("Ctrl date "+ resultNow);

        if(resultNow < 1)
        {
            this.messageErrorDateTimeEvent="";
            redirect = "null";
            return redirect;
        }else{
            try{
                transaction.begin();
                addressService.addAddress(address,em);
                events.setAddressesByIdAddress(address);
                eventsService.addEvent(events,em);
                transaction.commit();
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
            initFormEvent();
            return  redirect;
        }
    }

    public String submitFormUpdateEvents() {
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EventsService eventsService = new EventsService();
        DiveSiteCharacteristicService diveSiteCharacteristicService = new DiveSiteCharacteristicService();
        EntityTransaction transaction = em.getTransaction();
        Mail email = new Mail();
        Document doc = new Document();
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        LocalDate now = LocalDate.now();
        String dateEventPattern = "ddMMyyyy-HHmmss";
        String isoDatePattern = "dd/MM/yyyy";
        String isoTimePattern = "HH:mm";
        SimpleDateFormat dateEventFormat = new SimpleDateFormat(dateEventPattern);
        String dateTimeEvent = dateEventFormat.format(events.getDateTimeEvent());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String dateEvent = simpleDateFormat.format(events.getDateTimeEvent());
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(isoTimePattern);
        String dateTime = simpleTimeFormat.format(events.getDateTimeEvent());
        int resultNow = dateEvent.compareTo(String.valueOf(now));
        ProcessUtils.debug("Ctrl date " + resultNow);

        String filename;
        String source;
        if (resultNow < 1) {
            this.messageErrorDateTimeEvent = "";
            redirect = "null";
            return redirect;
        } else {
            try {
                transaction.begin();
                eventsService.updateEvent(events, em);
                transaction.commit();
                confirmUpdate();

            } catch (Exception e) {
                redirect = "null";
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                em.close();
            }

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
            LocalDateTime nowMail = LocalDateTime.now();
            filename = "Reservation" + "_" +events.getEventCategoriesByIdEventCategory().getEventCategoryLabel()+dateTimeEvent +dtf.format(nowMail)+ ".pdf";
            source = "C:\\Users\\devog\\IdeaProjects\\chasa\\src\\main\\webapp\\PDF\\" + filename;
            //allCharacteristics=diveSiteCharacteristicService.findallDiveSiteCharacteristicsByIdDiveSite(events.getDiveSitesByIdDiveSite().getIdDiveSite(),em);
            //ProcessUtils.debug(""+allCharacteristics.size());
            try {

                PdfWriter.getInstance(doc, new FileOutputStream(source));
                doc.open();
                Paragraph p = new Paragraph();
                Paragraph p1 = new Paragraph();


                String summary = bundle.getString("summary");
                String category = bundle.getString("category.event");
                String address = bundle.getString("address");
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
                p.add("\n "+ category+" : " + events.getEventCategoriesByIdEventCategory().getEventCategoryLabel()
                     +"\n "+ address+" : " + events.getAddressesByIdAddress().getStreet()+" "+ events.getAddressesByIdAddress().getNumber()+ " "+ events.getAddressesByIdAddress().getBox()+" "+events.getAddressesByIdAddress().getIdCity().getPostalCode()+" "+ events.getAddressesByIdAddress().getIdCity().getCityLabel()
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

                    p1.add("\n "+ addressDiveSite+" : " + events.getDiveSitesByIdDiveSite().getAddressesByIdAddress().getStreet()+" "+ events.getAddressesByIdAddress().getNumber()+ " "+ events.getAddressesByIdAddress().getBox()+" "+events.getAddressesByIdAddress().getIdCity().getPostalCode()+" "+ events.getAddressesByIdAddress().getIdCity().getCityLabel()
                           +"\n "+hyperbareName+" : "+ events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getHyperbaricChamberLabel()
                           +"\n "+addressHyperbare+" : "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getStreet()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getNumber()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getIdCity().getCityLabel());
                    doc.add(p1);
                }
                else if(events.getDiveSitesByIdDiveSite().getIdDiveSite()== 2){
                    Image image = Image.getInstance("C:\\Users\\devog\\IdeaProjects\\chasa\\src\\main\\webapp\\CSS\\Pictures\\Vodelée.jpg");
                    image.scaleAbsolute(550f, 400f);

                    doc.add(new Paragraph(characteristic+" :"+ events.getDiveSitesByIdDiveSite().getDiveSiteLabel(),f));
                    doc.add(image);
                    p1.add("\n "+ addressDiveSite+" : " + events.getDiveSitesByIdDiveSite().getAddressesByIdAddress().getStreet()+" "+ events.getAddressesByIdAddress().getNumber()+ " "+ events.getAddressesByIdAddress().getBox()+" "+events.getAddressesByIdAddress().getIdCity().getPostalCode()+" "+ events.getAddressesByIdAddress().getIdCity().getCityLabel()
                            +"\n "+hyperbareName+" : "+ events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getHyperbaricChamberLabel()
                            +"\n "+addressHyperbare+" : "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getStreet()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getNumber()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getIdCity().getCityLabel());
                    doc.add(p1);
                }
                else if(events.getDiveSitesByIdDiveSite().getIdDiveSite()== 3){
                    Image image = Image.getInstance("C:\\Users\\devog\\IdeaProjects\\chasa\\src\\main\\webapp\\CSS\\Pictures\\Vodecée.jpg");
                    image.scaleAbsolute(550f, 400f);

                    doc.add(new Paragraph(characteristic+" :"+ events.getDiveSitesByIdDiveSite().getDiveSiteLabel(),f));
                    doc.add(image);
                    p1.add("\n "+ addressDiveSite+" : " + events.getDiveSitesByIdDiveSite().getAddressesByIdAddress().getStreet()+" "+ events.getAddressesByIdAddress().getNumber()+ " "+ events.getAddressesByIdAddress().getBox()+" "+events.getAddressesByIdAddress().getIdCity().getPostalCode()+" "+ events.getAddressesByIdAddress().getIdCity().getCityLabel()
                            +"\n "+hyperbareName+" : "+ events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getHyperbaricChamberLabel()
                            +"\n "+addressHyperbare+" : "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getStreet()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getNumber()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getIdCity().getCityLabel());
                    doc.add(p1);
                }
                else if(events.getDiveSitesByIdDiveSite().getIdDiveSite()== 4){
                    Image image = Image.getInstance("C:\\Users\\devog\\IdeaProjects\\chasa\\src\\main\\webapp\\CSS\\Pictures\\Pate taille.jpg");
                    image.scaleAbsolute(550f, 400f);

                    doc.add(new Paragraph(characteristic+" :"+ events.getDiveSitesByIdDiveSite().getDiveSiteLabel(),f));
                    doc.add(image);
                    p1.add("\n "+ addressDiveSite+" : " + events.getDiveSitesByIdDiveSite().getAddressesByIdAddress().getStreet()+" "+ events.getAddressesByIdAddress().getNumber()+ " "+ events.getAddressesByIdAddress().getBox()+" "+events.getAddressesByIdAddress().getIdCity().getPostalCode()+" "+ events.getAddressesByIdAddress().getIdCity().getCityLabel()
                            +"\n "+hyperbareName+" : "+ events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getHyperbaricChamberLabel()
                            +"\n "+addressHyperbare+" : "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getStreet()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getNumber()+" "+events.getDiveSitesByIdDiveSite().getHyperbaricchambersByIdHyperbaricChamber().getAddressesByIdAdress().getIdCity().getCityLabel());
                    doc.add(p1);
                }
                Font f1 = new Font();
                f1.setColor(BaseColor.RED);
                f1.setStyle(Font.BOLD);
                doc.add(new Paragraph("\n\n"+ reminder, f1));
                doc.add(new Paragraph("\n\n"+ bubbles));

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
}
