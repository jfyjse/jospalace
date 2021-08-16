package com.joffy.jospalace.service.implementation;
import com.joffy.jospalace.entity.ListingEntity;
import com.joffy.jospalace.entity.UserEntity;
import com.joffy.jospalace.model.ListingAddRequestModel;
import com.joffy.jospalace.repository.ListingRepository;
import com.joffy.jospalace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class ListingServiceImpl {

    @Autowired
    ListingRepository listingRepository;
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    UserRepository userRepository;

    public void addToListing(ListingAddRequestModel listingAddItem, UserEntity userId) {
        ListingEntity listing = new ListingEntity();
        listing.setListingName(listingAddItem.getListingName());
        listing.setDescription(listingAddItem.getDescription());
        listing.setPrice(listingAddItem.getPrice());
        listing.setUserEntity(userId);
        listing.setImages("no image");
        System.out.println("userid   "+userId.getUserId());
        listingRepository.save(listing);
    }

    public List<ListingEntity> getAllListings() {
        List<ListingEntity> listingEntities = listingRepository.getAllListing();
        return listingEntities;
    }

    public void uploadImage(MultipartFile file,Long listingId, String uid) throws IOException {

        Optional<ListingEntity> listingEntity = listingRepository.findById(listingId);
        ListingEntity findListing = listingEntity.get();
        if(listingEntity.isEmpty()){ throw new IllegalStateException("no listings found");}
        String path ="/home/joffy/Desktop/spring boot/jospalace/images/" + listingId + " " + uid;
        file.transferTo(new File(path));
        findListing.setImages(path);
        listingRepository.save(findListing);
    }


    public void deleteImage(Long listingId, String uid) throws IOException {

        Optional<ListingEntity> listingEntity = listingRepository.findById(listingId);
        ListingEntity findListing = listingEntity.get();
        if(listingEntity.isEmpty()){ throw new IllegalStateException("no listings found");}

        Path path = Path.of("/home/joffy/Desktop/spring boot/jospalace/images/" + listingId + " " + uid);
        Files.delete(path);
        findListing.setImages("deleted");
        listingRepository.save(findListing);
    }


    public void deleteListing(Long listingId, String userId) throws IOException {

        Path path = Path.of("/home/joffy/Desktop/spring boot/jospalace/images/" + listingId + " " + userId);
        listingRepository.deleteByListingId(listingId);
        Files.delete(path);
    }

    public void sendMail(Long listingId, String userId) throws MessagingException {
        Long listingUserId = listingRepository.findUserByListingId(listingId);//owner
        Optional<UserEntity> user = userRepository.findById(listingUserId);
        String owner = user.get().getName();
        String ownerEmail = user.get().getEmail();
        String fileName = user.get().getUserId();
        String productName = listingRepository.findProductName(listingId);
        UserEntity buy =userRepository.findByUserId(userId);
        String buyer = buy.getName();
        String buyEmail = buy.getEmail();
        Integer buyPhone = buy.getPhone();

        String bodyMessage = "hi, "+ owner + " your listing with id " +listingId + " and listing name " +productName+
                " has been requested by "+buyer
                +" interested buyer's contact info is provided for further escalation  "+" email : " +buyEmail +
                " phone no: "+buyPhone + " if you have uploaded image of your product it has been attached to this mail.";
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg,true);
        helper.setTo(ownerEmail);
        helper.setSubject("Your Listing is been enquired");
        helper.setText(bodyMessage);

        FileSystemResource file = new FileSystemResource(new File
                ("/home/joffy/Desktop/spring boot/jospalace/images/"+listingId+" "+fileName));
        if (file.exists()) {
            helper.addAttachment("yourListing.png", file);
        }
        javaMailSender.send(msg);

    }
}
