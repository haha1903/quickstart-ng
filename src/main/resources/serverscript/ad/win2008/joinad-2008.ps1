#ps1
Function SetScriptContinue([string]$NewServerName) 
{ 
    $AdminKey = "HKLM:" 
    $WinLogonKey = $AdminKey+"\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon"
    $RunOnceKey = $AdminKey + "\Software\Microsoft\Windows\CurrentVersion\RunOnce" 
    set-itemproperty $RunOnceKey "ConfigureServer" ('C:\Windows\System32\WindowsPowerShell\v1.0\Powershell.exe -executionPolicy Unrestricted -File "C:\temp\joinad.ps1" >>c:\temp\joinad.log' ) 
    set-itemproperty $WinLogonKey "DefaultUserName" "administrator" 
    set-itemproperty $WinLogonKey "DefaultPassword" '4rfv$RFV'
    set-itemproperty $WinLogonKey "AutoAdminLogon" "1" 
    set-itemproperty $WinLogonKey "AutoLogonCount" "1" 
#    set-itemproperty $WinLogonKey "DefaultDomainName" "" 
} 
$ChkFile = "C:\temp\joinad.ps1"
$FileExists = Test-Path $ChkFile
If ($FileExists -eq $False) {
#set ip
md c:\temp
cd c:\temp
echo '$myname=hostname'>>joinad.ps1
echo '$admin=[adsi]("WinNT://" + $myname + "/administrator, user")'>>joinad.ps1
echo '$admin.psbase.invoke("SetPassword", ''3edc#EDC'')'>>joinad.ps1
echo '$admin.psbase.CommitChanges()'>>joinad.ps1
echo '$dnshost="192.168.101.4"'>>joinad.ps1
echo '$AdminKey = "HKLM:"'>>joinad.ps1
echo '$WinLogonKey = $AdminKey+"\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon"' >>joinad.ps1
echo '$RunOnceKey = $AdminKey + "\Software\Microsoft\Windows\CurrentVersion\RunOnce"' >>joinad.ps1
echo 'set-itemproperty $WinLogonKey "DefaultUserName" " "' >>joinad.ps1
echo 'set-itemproperty $WinLogonKey "DefaultPassword" " "' >>joinad.ps1
echo 'set-itemproperty $WinLogonKey "AutoAdminLogon" " "' >>joinad.ps1
echo 'set-itemproperty $WinLogonKey "AutoLogonCount" " "' >>joinad.ps1
echo '$wmi = Get-WmiObject win32_networkadapterconfiguration -filter "ipenabled = ''true''" '>>joinad.ps1
echo '$ipaddr=$wmi.IPAddress[0] '>>joinad.ps1
echo '$wmi.EnableStatic($ipaddr, "255.255.255.0") '>>joinad.ps1
echo '$wmi.SetGateways("192.168.101.1", 1) '>>joinad.ps1
echo '$wmi.SetDNSServerSearchOrder($dnshost) '>>joinad.ps1
echo '[System.Threading.Thread]::Sleep(10000)'>>joinad.ps1
echo '#join ad '>>joinad.ps1
echo '$domain = "test.com" '>>joinad.ps1
echo '$user = "administrator" '>>joinad.ps1
echo '$password = ConvertTo-SecureString ''4rfv$RFV'' -asPlainText -Force '>>joinad.ps1
echo '$username = "$domain\$user"  '>>joinad.ps1
echo '$credential = New-Object System.Management.Automation.PSCredential($username,$password) '>>joinad.ps1
echo 'Add-Computer -DomainName $domain -Credential $credential '>>joinad.ps1
echo 'Remove-Item $MyINvocation.InvocationName' >>joinad.ps1
echo 'shutdown /r /t 0' >>joinad.ps1
SetScriptContinue("postscript")
}
