from flask import Flask
from flask import jsonify
from flask import request
app = Flask(__name__)

@app.route("/")
def hello():
    return "Hello World!"


import requests
from bs4 import BeautifulSoup

@app.route('/login', methods=["POST"])
def login_action():
    try:
        data = request.get_json(force=True)
        print data	
        c = requests.Session()
        c.get("https://webkiosk.jiit.ac.in")
        params ={'x':'',
            'txtInst':'Institute',
            'InstCode':'JIIT',
            'txtuType':'Member Type',
            'UserType':'S',
            'txtCode':'Enrollment No',
            'MemberCode':data['eno'],
            'DOB':'DOB',
            'DATE1':data['dob'],
            'txtPin':'Password/Pin',
            'Password':data['password'],
            'BTNSubmit':'Submit'}
        cook=c.cookies['JSESSIONID']
        cooki=dict(JSESSIONID=cook)
        reslogin=c.post("https://webkiosk.jiit.ac.in/CommonFiles/UserActionn.jsp", data=params,cookies=cooki)
        if "Error1.jpg" in reslogin.content:
            html = BeautifulSoup(reslogin.content, 'html.parser')
            c.close()
            return jsonify(error=html.b.font.text), 500
        else:
            cgpa_resp = c.get("https://webkiosk.jiit.ac.in/StudentFiles/Academic/StudSubjectTaken.jsp", cookies=cooki)
            html = BeautifulSoup(cgpa_resp.content, 'html.parser')
            rows = html.find_all("table")
            subjectlists  = rows[2].find_all("tr")
            subject_list = []
            for subjectlist in subjectlists:
                cols = subjectlist.find_all('td')
                temp = {"subject" : cols[1].text.strip()}
                subject_list.append(temp)
          
            return jsonify(subject = subject_list),200
            c.close()
    except Exception as e:
        return jsonify(error = repr(e)), 500
	


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=int("5001"),debug = True)
