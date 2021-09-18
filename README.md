# itau-extractor

Available on heroku over: *<https://itau-extractor.herokuapp.com/>*

Developed by ***[Felipe Zanichelli](flp313@gmail.com)***



Endpoint list:

1) Upload new file
```
POST /upload/extract/month/05/year/2021 HTTP/1.1
Host: localhost:8080
Content-Length: 231
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="file"; filename="/C:/Users/XPTO/Downloads/Extrato Conta Corrente-270820212021.txt"
Content-Type: text/plain

(data)
----WebKitFormBoundary7MA4YWxkTrZu0gW
```



2) Get all transactions in a month

```
GET /transaction/findall/month/5/year/2021 HTTP/1.1
Host: localhost:8080
```