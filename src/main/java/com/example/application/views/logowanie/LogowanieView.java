//package com.example.application.views.logowanie;
//
//import com.vaadin.flow.component.Composite;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.login.LoginForm;
//import com.vaadin.flow.component.orderedlayout.FlexComponent;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.router.Menu;
//import com.vaadin.flow.router.PageTitle;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.server.auth.AnonymousAllowed;
//import org.vaadin.lineawesome.LineAwesomeIconUrl;
//
//@PageTitle("Logowanie")
//@Route("")
//@Menu(order = 3, icon = LineAwesomeIconUrl.USER)
//@AnonymousAllowed
//public class LogowanieView extends Composite<VerticalLayout> {
//
//    public LogowanieView() {
//        VerticalLayout layoutColumn2 = new VerticalLayout();
//        LoginForm loginForm = new LoginForm();
//        Button buttonSecondary = new Button();
//        getContent().setWidth("100%");
//        getContent().getStyle().set("flex-grow", "1");
//        layoutColumn2.setWidthFull();
//        getContent().setFlexGrow(1.0, layoutColumn2);
//        layoutColumn2.setWidth("100%");
//        layoutColumn2.getStyle().set("flex-grow", "1");
//        layoutColumn2.setAlignSelf(FlexComponent.Alignment.CENTER, loginForm);
////        loginForm.setWidth("min-content");
////        loginForm.setMaxWidth("800px");
//        buttonSecondary.setText("Sign in");
//        layoutColumn2.setAlignSelf(FlexComponent.Alignment.CENTER, buttonSecondary);
//        buttonSecondary.setWidth("min-content");
//        getContent().add(layoutColumn2);
//        layoutColumn2.add(loginForm);
//        layoutColumn2.add(buttonSecondary);
//    }
//}
