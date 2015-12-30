package juke.web.web;

import com.jukeb.domain.picture.PictureListChangedEvent;
import juke.web.config.WebSocketMessageBrokerConfigurer;
import juke.web.party.PartyController;
import juke.web.picture.PictureResoure;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PictureListChangedListener implements ApplicationListener<PictureListChangedEvent> {
	private @Autowired
	SimpMessageSendingOperations template;
	private @Autowired
	MapperFacade mapperFacade;
	
	@Override
	public void onApplicationEvent(PictureListChangedEvent event) {
		Stream<PictureResoure> pictureResources = event.getPictureList().stream().map(p -> new PartyController().convertPictureToResource(p.getParty().getName(), p));
		template.convertAndSend(WebSocketMessageBrokerConfigurer.TOPIC + "/pictures", pictureResources.collect(Collectors.toList()));
	}	
}