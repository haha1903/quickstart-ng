#ps1
$ChkFile = "C:\temp\addeploy.ps1"
$FileExists = Test-Path $ChkFile
If ($FileExists -eq $False) {
md c:\temp
cd c:\temp
echo 'Install-WindowsFeature -name AD-Domain-Services -IncludeManagementTools' >>c:\temp\addeploy.ps1
echo 'Install-ADDSForest -DatabasePath "C:\Windows\NTDS" -DomainMode "Win2008" -DomainName "testdomain.com" -DomainNetBIOSName "CORP" -ForestMode "Win2008" -InstallDNS:$false -LogPath "C:\Windows\NTDS" -NoRebootOnCompletion:$false -SYSVOLPath "C:\Windows\SYSVOL" -SafeModeAdministratorPassword (convertto-securestring ''4rfv$RFV'' -asplaintext -force) -Force:$true' >>c:\temp\addeploy.ps1
}
