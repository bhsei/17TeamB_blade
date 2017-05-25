package cn.hhchat.BladeTest.utils;

import org.junit.Test;

/**
 * Created this one by HMH on 2017/5/23.
 */
public class StringUtilTest {
    @Test
    public void isEmpty() throws Exception {
        String testStr1 = "";
        String testStr2 = null;
        String testStr3 = "hola";
        String testStr4 = "asdflkjw;lekrj;lixzjvllkzxdnjhlkejhlkwuerlkjhdlvkjhxlckjvh lkjhasd lkjfhwelkrjhlkjxh vlkjxhcv lkasjdhflk jwqehr lkjshd flkjaxh vlzkjxhc vzkxjhbgl kjwhe lrkuhqwler aslh vljkhx vxbmc,nvb kjhds rjhwe lrkjh lskjdhf zxjv ljhsadl kfjwhelr tkjshad fkjxzhcv lkjhasdlkjfh wieuryt dkjvh zlkxjhv lzkxjchl kjgqwlejk rp   woeiur aslkjdhf alskjdg hasldkjfh laskjdhf alfhdjlkhjasdlfk jhzxmjvb kjhwq eiuhr lasdkjhf zxkljhv amsn,db lqkwjeth";
        String testStr5 = "中文测试";
        String testStr6 = "🤣🤣🤣";
        assert StringUtil.isEmpty(testStr1);
        assert StringUtil.isEmpty(testStr2);
        assert !StringUtil.isEmpty(testStr3);
        assert !StringUtil.isEmpty(testStr4);
        assert !StringUtil.isEmpty(testStr5);
        assert !StringUtil.isEmpty(testStr6);
    }

}