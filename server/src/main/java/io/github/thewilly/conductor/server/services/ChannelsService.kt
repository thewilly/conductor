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

    fun create(name:String, freq:String, ctcss:String): Boolean {
        if(channelsRepo!!.findByName(name) != null) {
            return false;
        }
        channelsRepo!!.save(Channel(name = name, freq = freq, ctcss = ctcss))
        return true;
    }

    fun changeName(channelName: String, name: String) {
        val storedChannel = channelsRepo!!.findByName(channelName)
        storedChannel!!.name = name;
        channelsRepo.save(storedChannel)
    }

    fun changeFreq(channelName: String, freq: String, ctcssFreq: String) {
        val storedChannel = channelsRepo!!.findByName(channelName)
        storedChannel!!.freq = freq;
        storedChannel!!.ctcss = ctcssFreq;
        channelsRepo!!.save(storedChannel);
    }

    fun remove(channelId: String) {
        val storedChannel = channelsRepo!!.findByChannelId(channelId)
        channelsRepo.delete(storedChannel)
    }

    fun numberOfChannels(): Int {
        return channelsRepo!!.findAll().size
    }

    fun getAllChannels(): List<Channel> {
        return channelsRepo!!.findAll()
    }
}
