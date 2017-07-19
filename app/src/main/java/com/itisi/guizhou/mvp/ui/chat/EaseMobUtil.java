package com.itisi.guizhou.mvp.ui.chat;

import android.content.Context;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;
import java.util.Map;

/**
 * **********************
 * 功 能:环信---工具
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/6/16 10:34
 * 修改人:itisi
 * 修改时间: 2017/6/16 10:34
 * 修改内容:itisi
 * *********************
 */

public class EaseMobUtil {

    private static final String TAG = "EaseMobUtil";
    // TODO: 2017/6/16  状态暂时未加
//    通过 message 设置消息的发送及接收状态。
//    message.setMessageStatusCallback(new EMCallBack(){});

    public static void init(Context context){
//        EaseUI.getInstance().init(this, null);//环信即时通讯
        EMOptions options=new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 设置是否需要已读回执
        options.setRequireAck(true);
        // 设置是否需要已送达回执
        options.setRequireDeliveryAck(true);
//        设置对方是否已读
//        EMClient.getInstance().chatManager().ackMessageRead("toid","msgid");
        //这设置过时?
//        建议初始化SDK的时候设置成每个会话默认load一条消息，节省加载会话的时间，
//        方法为： options.setNumberOfMessagesLoaded(1);
        EMClient.getInstance().init(context,options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    /**
     * 登陆
     *
     * @param username
     * @param pasword
     * @param callBack
     */
    public static void login(String username, String pasword, EMCallBack callBack) {
        EMClient.getInstance().login(username, pasword, callBack);
    }

    /**
     * 退出
     * @param isUnbindDevice
     * @param callBack
     */
    public static void logout(boolean isUnbindDevice, EMCallBack callBack) {
        EMClient.getInstance().logout(isUnbindDevice, callBack);
        //同步会话列表的
        EMClient.getInstance().groupManager().loadAllGroups();
        EMClient.getInstance().chatManager().loadAllConversations();


    }

    /**
     * 注册一个连接状态监听
     * EMError.USER_LOGIN_ANOTHER_DEVICE，则认为是有同一个账号异地登录；
     * 若服务器返回的参数值为EMError.USER_REMOVED，则是账号在后台被删除。
     *
     * @param listener
     */
    public static void addConnectionListener(EMConnectionListener listener) {
        EMClient.getInstance().addConnectionListener(listener);
    }

    /**
     * 发送文本消息
     * @param chatType       聊天类型
     * @param toUserId       为对方用户或者群聊的id
     * @param messageContent 文本消息
     */
    public static void sendTxtMessage(EMMessage.ChatType chatType, String toUserId, String messageContent) {
        EMMessage message = EMMessage.createTxtSendMessage(messageContent, toUserId);
        // TODO: 2017/6/16 暂时不管 聊天室可能要特别一点 但是暂时不用
//        if (chatType== EMMessage.ChatType.Chat){
//
//        }else if(chatType== EMMessage.ChatType.GroupChat){
//
//        }else if(chatType== EMMessage.ChatType.ChatRoom){
//
//        }else{
//            Log.e(TAG, "sendTxtMessage: 聊天类型不对" );
//        }
        message.setChatType(chatType);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    /**
     * 发送文本消息-默认单聊,如需其他类型,请用重载
     *
     * @param toUserId       为对方用户或者群聊的id
     * @param messageContent 文本消息
     */
    public static void sendTxtMessage(String toUserId, String messageContent) {
        sendTxtMessage(EMMessage.ChatType.Chat, toUserId, messageContent);
    }

    /**
     * 发送图片消息
     *
     * @param chatType          聊天类型
     * @param toUserId          为对方用户或者群聊的id
     * @param localPath         图片本地路径
     * @param isSendOriginalImg false为不发送原图（默认超过100k的图片会压缩后发给对方），需要发送原图传true
     */
    public static void sendImgMessage(EMMessage.ChatType chatType, String toUserId, String localPath
            , boolean isSendOriginalImg) {
        EMMessage message = EMMessage.createImageSendMessage(localPath, isSendOriginalImg, toUserId);
        message.setChatType(chatType);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    /**
     * 发送图片消息-默认不发生原图且为单聊
     * @param toUserId  为对方用户或者群聊的id
     * @param localPath 图片本地路径
     */
    public static void sendImgMessage(String toUserId, String localPath) {
        sendImgMessage(EMMessage.ChatType.Chat, toUserId, localPath, false);
    }

    /**
     * 发送语音
     *
     * @param chatType   聊天类型
     * @param toUserId   为对方用户或者群聊的id
     * @param localPath  语音的本地路径
     * @param timeLength 语音时长(单位s)
     */
    public static void sendVoiceMessage(EMMessage.ChatType chatType, String toUserId, String localPath
            , int timeLength) {
        EMMessage message = EMMessage.createVoiceSendMessage(localPath, timeLength, toUserId);
        message.setChatType(chatType);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    /**
     * 发送语音消息
     * @param toUserId   为对方用户或者群聊的id
     * @param localPath  语音本地路径
     * @param timeLength 语音时长(单位s)
     */
    public static void sendVoiceMessage(String toUserId, String localPath, int timeLength) {
        sendVoiceMessage(EMMessage.ChatType.Chat, toUserId, localPath, timeLength);
    }

    /**
     * 发送视频
     * @param chatType   聊天类型
     * @param toUserId   为对方用户或者群聊的id
     * @param localPath  视频的本地路径
     * @param thumbPath  视频预览图
     * @param timeLength 视频时长 (单位s)
     */
    public static void sendVideoMessage(EMMessage.ChatType chatType, String toUserId, String localPath
            , String thumbPath, int timeLength) {
        EMMessage message = EMMessage.createVideoSendMessage(localPath, thumbPath, timeLength, toUserId);
        message.setChatType(chatType);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    /**
     * 发送视频消息
     * @param toUserId   为对方用户或者群聊的id
     * @param localPath  视频的本地路径
     * @param thumbPath  视频预览图
     * @param timeLength 视频时长(单位s)
     */
    public static void sendVideoMessage(String toUserId, String localPath, String thumbPath, int timeLength) {
        sendVideoMessage(EMMessage.ChatType.Chat, toUserId, localPath, thumbPath, timeLength);
    }

    /**
     * 发送定位信息
     * @param chatType        聊天类型
     * @param toUserId        为对方用户或者群聊的id
     * @param latitude        纬度
     * @param longitude       经度
     * @param locationAddress 定位地址信息
     */
    public static void sendLocationMessage(EMMessage.ChatType chatType, String toUserId,
                                           double latitude, double longitude, String locationAddress
    ) {
        EMMessage message = EMMessage.createLocationSendMessage(latitude, longitude, locationAddress, toUserId);
        message.setChatType(chatType);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    /**
     * 发送定位信息
     * @param toUserId        为对方用户或者群聊的id
     * @param latitude        纬度
     * @param longitude       经度
     * @param locationAddress 定位地址信息
     */
    public static void sendLocationMessage(String toUserId, double latitude, double longitude, String locationAddress) {
        sendLocationMessage(EMMessage.ChatType.Chat, toUserId, latitude, longitude, locationAddress);
    }

    /**
     * 发送文件消息
     * @param chatType  聊天类型
     * @param toUserId  为对方用户或者群聊的id
     * @param localPath 文件本地路径
     */
    public static void sendFileMessage(EMMessage.ChatType chatType, String toUserId, String localPath) {
        EMMessage message = EMMessage.createFileSendMessage(localPath, toUserId);
        message.setChatType(chatType);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    /**
     * 发送文件消息
     * @param toUserId  为对方用户或者群聊的id
     * @param localPath 文件本地路径
     */
    public static void sendFileMessage(String toUserId, String localPath) {
        sendFileMessage(EMMessage.ChatType.Chat, toUserId, localPath);
    }

    // TODO: 2017/6/16 未完成 1.透传消息 2.扩展消息

    /**
     * 添加接受消息的监听器
     * @param listener
     */
    public static void addReceiveMessageListener(EMMessageListener listener) {
        EMClient.getInstance().chatManager().addMessageListener(listener);
    }

    /**
     * 移除消息接受监听器
     * 如在activity的onDestroy()时
     * @param listener
     */
    public static void removeReceiveMessageListener(EMMessageListener listener) {
        Log.i(TAG, "removeReceiveMessageListener: ");
        EMClient.getInstance().chatManager().removeMessageListener(listener);
    }

    /**
     * 获取此会话的所有消息
     * @param toUserId
     * @return 历史消息
     */
    public static List<EMMessage> getMessages(String toUserId){
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(toUserId);
        return conversation.getAllMessages();
    }

    /**
     * 获取指定消息id之前的 pagesize 条消息
     * //获取startMsgId之前的pagesize条消息，此方法获取的messages
     * SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中(环信原话)
     * @param toUserId 为对方用户或者群聊的id
     * @param startMsgId 消息id
     * @param pagesize 取多少条消息
     * @return 历史消息
     */
    public static List<EMMessage> getMessageBeforeStartMsgId(String toUserId, String startMsgId, int pagesize){
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(toUserId);
        return conversation.loadMoreMsgFromDB(startMsgId, pagesize);
    }

    /**
     * 获取未读消息数量
     * @param toUserId 为对方用户或者群聊的id
     * @return 未读数量
     */
    public static int getUnReadMsgCount(String toUserId) {
        EMConversation conversation = EMClient.getInstance().chatManager()
                .getConversation(toUserId);
        int count = conversation.getUnreadMsgCount();
        return count;
    }

    /**
     * 清空指定会话的未读消息数(未读---已读)
     * @param toUserId 为对方用户或者群聊的id
     */
    public static void makeAllMsgAsRead(String toUserId) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(toUserId);
        conversation.markAllMessagesAsRead();

    }

    /**
     * 指定消息id 设置为已读
     * @param toUserId 为对方用户或者群聊的id
     * @param msgId    消息id
     */
    public static void makeMsgAsReadByMsgId(String toUserId, String msgId) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(toUserId);
        conversation.markMessageAsRead(msgId);
    }

    /**
     * 消息 已读回执
     * @param toUserId
     * @param msgId
     */
    public static void makeMessageRead(String toUserId, String msgId){
        try {
            EMClient.getInstance().chatManager().ackMessageRead(toUserId,msgId);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空所有会话的未读消息
     */
    public static void makeAllConversationMsgAsRead() {
        EMClient.getInstance().chatManager().markAllConversationsAsRead();
    }

    /**
     * 获取消息总数
     *
     * @param toUserId 为对方用户或者群聊的id
     * @return 消息数量
     */
    public static int getMsgCountFromLocalDb(String toUserId) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(toUserId);
        return conversation.getAllMsgCount();
    }

    /**
     * 获取消息总数
     *
     * @param toUserId 为对方用户或者群聊的id
     * @return 消息数量
     */
    public static int getMsgCountFromMemary(String toUserId) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(toUserId);
        return conversation.getAllMessages().size();
    }

    /**
     * 获取所有会话
     * 如果出现偶尔返回的conversations的sizi为0，
     * 那很有可能是没有调用EMClient.getInstance().chatManager().loadAllConversations()，
     * 或者调用顺序不对
     *
     * @return
     */
    public static Map<String, EMConversation> getAllConversation() {
        Map<String, EMConversation> allConversations = EMClient.getInstance().chatManager().getAllConversations();
        return allConversations;
    }

    /**
     * 删除指定id的聊天记录
     *
     * @param toUserId 为对方用户或者群聊的id
     * @param isSave   是否保留聊天记录
     * @return boolean 删除成功或者失败
     */
    public static boolean deleteConversationMsg(String toUserId, boolean isSave) {
        return EMClient.getInstance().chatManager().deleteConversation(toUserId, isSave);
    }

    /**
     * 删除指定的聊天记录
     *
     * @param toUserId 为对方用户或者群聊的id
     * @param msgId    消息id
     */
    public static void deleteMsgById(String toUserId, String msgId) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(toUserId);
        conversation.removeMessage(msgId);
    }

    /**
     * 向消息数据库导入多条聊天记录
     * 在调用次函数时要保证，消息的发送方或者接收方是当前用户
     * 已经对函数做过速度优化， 推荐一次导入1000条数据 这是环信的原话
     * @param msgs
     */
    public static void importMsgToDB(List<EMMessage> msgs) {
        EMClient.getInstance().chatManager().importMessages(msgs);
    }

}
