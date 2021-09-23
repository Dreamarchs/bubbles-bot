@echo off
pushd %~dp0\..\proto\
echo Proto message directory %cd%
set "protodir=%cd%"
set "javadir=%protodir%\generated\java"
popd
pushd %~dp0\..\protobuf\cmake\Debug\
echo Generating Java code...
if not exist "%javadir%" mkdir "%javadir%"
call protoc.exe -I="%protodir%" --java_out="%javadir%" guild.proto
@REM call protoc.exe -I="%protodir%" --java_out="%cppdir%" shader.proto
@REM call protoc.exe -I="%protodir%" --java_out="%cppdir%" material.proto
@REM call protoc.exe -I="%protodir%" --java_out="%cppdir%" mesh.proto
popd
PAUSE
