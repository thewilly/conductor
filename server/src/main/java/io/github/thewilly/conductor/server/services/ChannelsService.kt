package io.github.thewilly.conductor.server.services

import io.github.thewilly.conductor.server.repositories.ChannelsRepository
import io.github.thewilly.conductor.server.types.Channel
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ChannelsService {

    @Autowired
    val channelsRepo: ChannelsRepository? = null

    fun changeFreq(channel: Channel, freq: String, ctcssFreq: String) {
        val repoChannel = channelsRepo!!.findByChannelName(channel.channelName)
        repoChannel.frequency = freq;
        repoChannel.ctcssFrequency = ctcssFreq;
        channelsRepo!!.save(repoChannel);
    }
}
