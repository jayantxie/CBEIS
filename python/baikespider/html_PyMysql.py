import pymysql

# conn = pymysql.connect(host='127.0.0.1', user='root', passwd='root', db='crawler')


class htmlpymysql(object):
    
    def insert(self,url, title, date, summary):
        conn = pymysql.connect(host='127.0.0.1', port=3306, user='root', passwd='root', db='CBEIS',charset='utf8mb4')
        cur = conn.cursor()
        cur.execute("INSERT INTO crawler_data(url, title, newsdate, summary) VALUES(%s, %s, %s, %s)",(url, title, date, summary))
        conn.commit()
# print cur.description
        cur.close()
        conn.close()    

    def delete(self):
        conn = pymysql.connect(host='127.0.0.1', port=3306, user='root', passwd='root', db='CBEIS',charset='utf8mb4')
        cur = conn.cursor()
        cur.execute("Truncate table crawler_data")
        conn.commit()
# print cur.description
        cur.close()
        conn.close()
