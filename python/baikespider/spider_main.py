from baikespider import url_manager, html_download, html_parser,html_PyMysql
from symbol import except_clause
class SpiderMan(object):
    def __init__(self):
        self.urls = url_manager.UrlManager()
        self.downloader = html_download.HtmlDownload()
        self.parser = html_parser.HtmlParser()
        self.pymsql = html_PyMysql.htmlpymysql()
    
    def craw(self, root_url):
        html_PyMysql.htmlpymysql.delete(self)
        count = 0
        print('craw %d:%s'%(count,root_url))                      #打印URL
        html_cont = self.downloader.download(root_url)              #下载URL页面
        new_urls = self.parser.parser(root_url,html_cont)  #解析页面    #解析得urls和data
        try:
            self.urls.add_new_urls(new_urls)                           #new_urls添加
        except:
            print("craw failed")
        while self.urls.has_new_url():
            count = count + 1
            new_url = self.urls.get_new_url()
            print('craw %d:%s'%(count,new_url))
            html_cont = self.downloader.download(new_url)
            try:
                data = self.parser.parser1(new_url,html_cont)
                html_PyMysql.htmlpymysql.insert(self, data['url'], data['title'], data['date'], data['summary'])
            except:
                print("datagotfailed")


if __name__ == "__main__":
    root_url = "http://10.12.42.6/redir.php?catalog_id=127"
    obj_spider = SpiderMan()
    obj_spider.craw(root_url)
