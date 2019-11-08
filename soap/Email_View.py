from tkinter import *
import  re
import smtplib
import email
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.mime.image import MIMEImage
from email.mime.base import  MIMEBase
from email.mime.application import MIMEApplication
from email.header import Header
from suds.client import Client
from tkinter.messagebox import *
class View:
    def __init__(self):
        self.addr_set = []


        self.window = Tk()
        self.window.minsize(650, 400)
        self.window.maxsize(900, 600)
        self.label_addr = Label(self.window, text="Address:")
        self.Entry_email_addr = Entry(self.window, width=40)
        self.label_theme = Label(self.window, text="Theme: ")
        self.Entry_msg_theme = Entry(self.window, width=40)


        self.label_text_body = Label(self.window, text="Text:")
        self.Text_msg_body = Text(self.window)
        self.ListBox_addr_list = Listbox(self.window, selectmode=SINGLE, height=18, width=20)
        self.Scoll_text = Scrollbar()
        self.Scoll_addr_list = Scrollbar()

        self.Button_Send = Button(self.window, text="发送", command=self.send)
        self.Button_add_addr = Button(self.window, text="添加地址",command=self.add)
        self.Button_del_addr = Button(self.window, text='删除地址', command=self.delete)

        self.Scoll_text.config(command=self.Text_msg_body.yview)
        self.Scoll_addr_list.config(command=self.ListBox_addr_list.yview)

        self.Text_msg_body.config(yscrollcommand=self.Scoll_text.set)
        self.ListBox_addr_list.config(yscrollcommand=self.Scoll_addr_list.set)

        self.label_addr.grid(row=0, column=0)
        self.Entry_email_addr.grid(row=0, column=1)
        self.Button_add_addr.grid(row=0, column=3)

        self.label_theme.grid(row=1, column=0)
        self.Entry_msg_theme.grid(row=1, column=1)

        self.label_text_body.grid(row=2, column=0)
        self.Text_msg_body.grid(row=2, column=1)
        self.Scoll_text.grid(row=2, column=2, ipady=132)
        self.ListBox_addr_list.grid(row=2, column=3)
        self.Scoll_addr_list.grid(row=2, column=4, ipady=132)

        self.Button_Send.grid(row=3, column=1, sticky=S)
        self.Button_del_addr.grid(row=3, column=3, sticky=S)
        self.window.title("Email")
        self.window.mainloop()

    def send(self):
        self.Interaction()

    def Interaction(self):
        str_email_addr = self.Entry_email_addr.get()
        str_email_theme = self.Entry_msg_theme.get()
        str_msg_body = self.Text_msg_body.get(1.0, END)
        if self.ListBox_addr_list.get(1) != "":
            str_email_addr = (self.ListBox_addr_list.get(0, END))
            print(str_email_addr)
        if str_msg_body == "/n" or str_msg_body == "":
            showinfo(title="tips", message="No message written")
            return
        if str_email_addr == "" and len(self.addr_set) == 0:
            showinfo(title="Tips", message="No address")
            return
        self.fuc_email_transmit(str_email_addr, str_email_theme+"\n"+str_msg_body)

    def fuc_email_transmit(self, _url, _payload):
        host = 'localhost'
        port = 8000
        client = Client('http://%s:%s/?wsdl' % (host, port))
        if (not isinstance(_url, tuple)) and len(_url) != 0:
            is_vaild = client.service.Valid(_url)
            if is_vaild[0][0] == 'Y':
                is_success = client.service.SendEmail(_url, _payload)
                if is_success[0][0] == 'Y':
                    showinfo(title="Tips", message="发送成功")
        else:
            for x in _url:
                is_vaild = client.service.Valid(x)
                print(x)
                if is_vaild == 'N':
                    showinfo(title="Tips", message="s% is unavailable" % x)
                    return
            _url1 = {"string": _url}
            is_success = client.service.SendEmailBatch(_url1, _payload)
            if is_success[0][0] == 'Y':
                showinfo(title="Tips", message="发送成功")

    def delete(self):
        try:
            str_del = self.ListBox_addr_list.selection_get()
            self.ListBox_addr_list.delete(ACTIVE)
            self.addr_set.remove(str_del)
        except:
            pass

    def add(self):
        str_temp_addr = self.Entry_email_addr.get()
        self.addr_set.append(str_temp_addr)
        self.ListBox_addr_list.insert(END, str_temp_addr)
        self.Entry_email_addr.delete(END)

    """def fuc_email_transmit(self, para_addr, para_theme, para_msg):
        username = "***"
        password = "***"
        replyto = "***"
        receivers = ['xxx@xx.com', 'xxx@xx.com']
        rcptto = '***'
        msg = MIMEMultipart('alternative')
        msg['Subject'] = Header('主题').encode("utf-8")
        msg['From'] = '%s <%s>' % (Header("发信昵称").encode("utf-8"), username)
        msg['To'] = rcptto
        msg['Reply-to'] = replyto
        msg['Message-id'] = email.utils.make_msgid()
        msg['Date'] = email.utils.formatdate()

        textplain = MIMEText('TEXT纯文本部分', _subtype='plain', _charset='UTF-8')
        msg.attach(textplain)

        texthtml = MIMEText('自定义超文本部分', _subtype='html', _charset='UTF-8')
        msg.attach(texthtml)
        try:
            client = smtplib.SMTP()
            client.connect('smtpdm.aliyun.com',25)
            client.set_debuglevel(0)
            client.login(username, password)
            client.sendmail(username, rcptto, msg.as_string())
            client.quit()
            print('success')
        except smtplib.SMTPConnectError:
            print ('邮件发送失败，连接失败')
            print(e.smtp_code, e.smtp_error)
        except smtplib.SMTPAuthenticationError:
            print('邮件发送失败，认证错误:')
            print(e.smtp_code, e.smtp_error)
        except smtplib.SMTPSenderRefused :
            print('邮件发送失败，发件人被拒绝:')
            print(e.smtp_code, e.smtp_error)
        except smtplib.SMTPRecipientsRefused:
            print('邮件发送失败，收件人被拒绝:')
            print(e.smtp_code, e.smtp_error)
        except smtplib.SMTPDataError:
            print('邮件发送失败，数据接收拒绝:')
            print(e.smtp_code, e.smtp_error)
        except smtplib.SMTPException:
            print('邮件发送失败, ')
            print(e.message)
        except Exception:
            print('邮件发送异常, ')
            print(str(e))
        """




View=View()

