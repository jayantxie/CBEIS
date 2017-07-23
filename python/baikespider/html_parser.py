from bs4 import BeautifulSoup
from setuptools.package_index import HREF
import re
from nt import link
from urllib.parse import urlparse
import time


class HtmlParser(object):
    
    
    def _get_new_urls(self, page_url, soup):
        new_urls = set()
        links = soup.find_all('a', href=re.compile(r'redir\.php\?catalog_id\S127\Sobject_id\S\d*'))
        page_url = "http://10.12.42.6/"
        for link in links:
            new_url = link["href"]
            new_full_url = page_url + new_url
            # 拼接成完整的url
            new_urls.add(new_full_url)
        return  new_urls


    def _get_new_data(self, page_url, soup):
        res_data = {}
        res_data['url'] = page_url
        title_node = soup.find("h1")
        res_data['title'] = title_node.get_text()
        date_regexp = re.compile("\d\d\d\d-\d\d-\d\d\s\d\d:\d\d")
        extranote = soup.find(text = date_regexp)
        nStr = ""
        nStr = nStr.join(extranote)
        date = re.findall(date_regexp,nStr)
        Sdate = ""
        Sdate = Sdate.join(date[0:])
        res_data['date'] = time.strptime(Sdate, '%Y-%m-%d %H:%M')
        summary_node = soup.find(align="left")
        if summary_node is None:
            print ("None")
        else:
            res_data['summary'] = summary_node.get_text()
        return res_data


    def parser(self, page_url, html_cont):
        if page_url is None or html_cont is None:
            return
        soup = BeautifulSoup(html_cont, 'html.parser', from_encoding='gb2312')
        new_urls = self._get_new_urls(page_url,soup)
        return new_urls

    def parser1(self, page_url, html_cont):
        if page_url is None or html_cont is None:
            return
        soup = BeautifulSoup(html_cont, 'html.parser', from_encoding='gb2312')
        new_data = self._get_new_data(page_url, soup)
        return new_data
    



