package juke.web.config;

import juke.domain.song.Song;
import juke.web.song.SongResource;
import ma.glasnost.orika.Converter;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Created by rafa on 28/11/2015.
 */
@Component
public class JukebConfigurableMapper extends ConfigurableMapper

    {
        private @Autowired ApplicationContext context;

        public JukebConfigurableMapper() {
            super(false);
        }

        @Override
        @PostConstruct
        protected void init() {
            super.init();
        }

        @Override
        protected void configure(MapperFactory factory) {
            Map<String, Converter> converters = context.getBeansOfType(Converter.class);
            for (Converter<?, ?> converter : converters.values()) {
                factory.getConverterFactory().registerConverter(converter);
            }

            factory.classMap(Song.class, SongResource.class)
                    .byDefault()
                    .customize(new CustomMapper<Song, SongResource>() {
                        @Override
                        public void mapAtoB(Song song, SongResource songResource, MappingContext context) {
                            super.mapAtoB(song, songResource, context);
                            if (song.getVotes() != null) {

                                Long upvotes = song.getVotes().stream().filter(w -> w.getVote() >= 0).count();
                                Long downvotes = song.getVotes().stream().filter(w -> w.getVote() < 0).count();

                                songResource.setUpvotes(upvotes.intValue());
                                songResource.setDownvotes(downvotes.intValue());
                            }
                        }
                    })
                    .register();

            factory.classMap(Song.class, Song.class).exclude("id").mapNulls(false).mapNullsInReverse(false).byDefault().register();
/*
            factory.classMap(TaskRow.class, TaskRowDto.class)
                    .field("task.id", "id")
                    .field("task.name", "task")
                    .byDefault()
                    .register();
                    */
        }
    }

