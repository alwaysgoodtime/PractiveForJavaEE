package http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @author goodtime
 * @create 2020-03-05 3:36 下午
 * 抽取出了管道处理器的设置
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //向管道加入处理器，管道就是对channel加了各种处理器的包装类

        //得到管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        //1.加入一个netty提供的httpServerCodec codec是coder和decoder的缩写（编码与解码）

        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());//相当于给编解码器加了个名字，不加也会默认有一个
        //HttpServerCodec,其实用的是HttpObjectDecoder的lineparser和headerparser，对http传过来的数据进行解析

//        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
//        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));


        //2.增加一个自定义的处理器
        pipeline.addLast("MyTestHttpServerHandler",new TestServerHandler());


        }

}
