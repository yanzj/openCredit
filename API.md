# API document

## Credit

* `{baseurl}/credit/add`
  - Description: Add credit infomation of a person
  - Method: `POST`
  - FORM-DATA:
    ```
    "id":"4404420288808088888" # id string
    "data":"bbbc" ## credit data
    ```
  - Return:
    A number(creditId)

* `{baseurl}/credit/get`
  - Description: Get the list of credit data of a personal id
  - Method: `POST`
  - FORM-DATA:
    ```
    "id":"4404420288808088888" # id string
    ```
  - Return (Json):
    ``` JSON
    [
        {
            "id": 1,
            "uploader": "0x68c0f317204fdc15b63409f79f420b79ec4e3609",
            "type": 0,
            "data": "0x256f0a4f6b08925e9b6dcc392a344d18f7dc0228d9c7cf57b6acb9338b626a4d",
            "time": "1560257591959"
        },
        {
            ...
        }
    ]
    ```

* `{baseurl}/record/add`
  - Description: Add record of a specific credit
  - Method: `POST`
  - FORM-DATA:
    ```
    "creditDataId":"4404420288808088888" # credit id string from `{baseurl}/credit/get`
    "uploader":"bbbc" ## `uploader` from `{baseurl}/credit/get`
    ```
  - Return:
    ```
    {
        "applicant": "0x68c0f317204fdc15b63409f79f420b79ec4e3609",
        "uploader": "0x0148947262ec5e21739fe3a931c29e8b84ee34a0",
        "id": 1,
        "creditDataId": 1,
        "time": 1560254078475,
        "score": 0,
        "sent": false,
        "scored": false
    }
    ```

* `{baseurl}/record/check`
  - Description: Checks if the record is in the block chain
  - Method: `POST`
  - FORM-DATA:
    ```
    "applicant":
    "uploader":
    "recordId":
    "creditDataId":
    ```
  - Return (Json):
    ``` JSON
    {"isSuccess":false} # if can find the record in block chain
    ```

* `{baseurl}/record/ifSend`
  - Description: The uploader decide if send the original data
  - Method: `POST`
  - FORM-DATA:
    ```
    "recordId":"4404420288808088888"
    "isSend":true/false
    ```
  - Return (Json):
    ``` JSON
    {"isSuccess":false}
    ```

    if the response is empty

    ``` JSON
    {
        "timestamp": "2019-06-12T17:08:02.043+0000",
        "status": 500,
        "error": "Internal Server Error",
        "message": "response empty!",
        "path": "/credit/ifSend"
    }
    ```

* `{baseurl}/record/get`
  - Description: Get a record by a specific id
  - Method: `POST`
  - FORM-DATA:
    ```
    "recordId":"4404420288808088888"
    ```
  - Return (Json):
    ``` JSON
    {
        "applicant": "0x68c0f317204fdc15b63409f79f420b79ec4e3609",
        "uploader": "0x0148947262ec5e21739fe3a931c29e8b84ee34a0",
        "id": 1,
        "creditDataId": 1,
        "time": 1560254078475,
        "score": 0,
        "sent": false,
        "scored": false
    }
    ```

    If the record not exit, get an standard error:
    
    ``` JSON
    {
        "timestamp": "2019-06-12T17:08:02.043+0000",
        "status": 500,
        "error": "Internal Server Error",
        "message": "NULL",
        "path": "/record/get"
    }
    ```
  
* `{baseurl}/record/score`
  - Description: Give the record score
  - Method: `POST`
  - FORM-DATA:
    ```
    "recordId":"4404420288808088888"
    "score":60
    ```
  - Return (Json):
    the same as `/credit/ifSend`