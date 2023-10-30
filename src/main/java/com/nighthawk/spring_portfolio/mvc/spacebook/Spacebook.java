// package com.nighthawk.spring_portfolio.mvc.spacebook;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.stereotype.Component;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Lob;
// import jakarta.persistence.OneToOne;
// import javassist.Loader.Simple;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @Entity
// @Component
// @Configuration

// public class Spacebook {
//     @Id
//     @GeneratedValue(strategy = GenerationType.AUTO)
//     private Long id;

//     @Column(unique = true)
//     private String spacebook;

//     private Integer like;
//     private Integer dislike;

//     @Lob
//     private byte[] image;
// }
