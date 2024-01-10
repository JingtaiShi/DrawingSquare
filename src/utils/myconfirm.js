/**
 * 信息确认弹框
 */
import { MessageBox, Message } from "element-ui";
export default function myconfirm(text) {
    return new Promise((resolve, reject) => {
        MessageBox.confirm(text, 'System Information', {
            confirmButtonText: 'Confirm',
            cancelButtonText: 'Cancel',
            type: 'warning'
        }).then(() => {
            resolve(true)
        }).catch(() => {
            Message.warning('Canceled')
            reject(false)
        })
    }).catch(()=>{

    })
}