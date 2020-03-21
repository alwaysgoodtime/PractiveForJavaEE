package http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @author goodtime
 * @create 2020-03-05 3:35 下午
 */

//说明：SimpleChannelInboundHandler是ChannelInboundHandler的子类，提供更多的功能
// HttpObject 表示的是客户端和服务端相互通讯的数据被封装成HttpObject

public class TestServerHandler extends SimpleChannelInboundHandler<HttpObject>{

    //读取客户端的数据
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject msg) throws Exception {

        //判断msg是不是httprequest请求
        //浏览器的默认请求是：io.netty.handler.codec.http.DefaultHttpRequest
        //如果只是文字，浏览器会发起两次请求，一个是请求内容，一个是请求网站的图表，显示在浏览器左上角
        //如果还有图片，浏览器会发送更多次请求，来请求页面上的图片，也算是浏览器的异步加载，优化用户体验
        if(msg instanceof HttpRequest){

            System.out.println("msg 类型=" + msg.getClass());
            System.out.println("客户端地址" + channelHandlerContext.channel().remoteAddress());

            //handler和pipeline一一对应，handler不会复用，每来一个socketchannel，就多一个pipeline和handler
            //虽然是一个浏览器，但因为http每次连接，只会发送一个请求，所以每次请求，都会新建一个socketchannel、对应
            //新建一个handler和pipeline
            System.out.println(channelHandlerContext.hashCode());
            System.out.println(channelHandlerContext.pipeline());

            HttpRequest httpRequest = (HttpRequest)msg;

            //获取uri
            URI uri = new URI(httpRequest.uri());

            //对特定的资源请求过滤，这里过滤的是favion.ico

            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求了网站icon,不做处理");
                return;
            }



            //回复信息给浏览器，要加相应字段，表示遵守http协议

            //回复的内容
            ByteBuf content = Unpooled.copiedBuffer("hello,我是服务器", CharsetUtil.UTF_8);

            //构造一个http的响应，即httpresponse

            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);//协议、状态码、内容

            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf-8");//设置返回的内容类型和字符编码

            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());//返回内容的长度

            response.headers().set(HttpHeaderNames.CONTENT_LANGUAGE,"zh_CN");


            //将构建好的response返回
            channelHandlerContext.writeAndFlush(response);

        }

    }
}
