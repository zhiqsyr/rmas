
        var HKEY_Root, HKEY_Path, HKEY_Key;
        HKEY_Root = "HKEY_CURRENT_USER";
        HKEY_Path = "\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
        //设置网页打印的页眉页脚为空  
        function PageSetup_Null() {
            try {
                var Wsh = new ActiveXObject("WScript.Shell");
                HKEY_Key = "header";
                Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
                HKEY_Key = "footer";
                Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
            }
            catch (e)
            { }
        }

        function printpage(myDiv) { //DIV控制打印
            PageSetup_Null();

            var newstr = document.getElementById(myDiv).innerHTML;
            var oldstr = document.body.innerHTML;
            document.body.innerHTML = newstr;
            window.print();
            document.body.innerHTML = oldstr;
            return false;
        }

        function preview() {
            PageSetup_Null();
            bdhtml = window.document.body.innerHTML;
            sprnstr = "<!--startprint-->";
            eprnstr = "<!--endprint-->";
            prnhtml = bdhtml.substr(bdhtml.indexOf(sprnstr) + 17);
            prnhtml = prnhtml.substring(0, prnhtml.indexOf(eprnstr));
            window.document.body.innerHTML = prnhtml;
            window.print();
        }

        function FunctionReturn() {
            history.go(-1);
        }
