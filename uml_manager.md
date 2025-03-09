# Manager

```mermaid

classDiagram

    class IManager~T, S~ {
        <<Interface>>
        + readAll(S) List~T~
        + removeAll(T) boolean
        + create(T) T
        + read(S) T
        + update(T) T
        + remove(T) boolean
    }

    class LogManager {
        + LogManager() 
        + update(Object) Object
        + remove(Object) boolean
        + removeAll(Object) boolean
        + readAll(Object) List~LogEntry~
        + read(Object) Object
        + create(Object) Object
    }

    class UserManager {
        + UserManager() 
        + update(User) User
        + logout(User) User
        + remove(User) User
        + login(User) User
        + register(User) User
    }

    class DeviceManager {
        + DeviceManager() 
        + removeAll(Object) boolean
        + read(Object) Object
        + remove(Object) boolean
        + update(Object) Object
        + create(Object) Device
        + readAll(Object) List~Device~
    }

    class ConnectionManager {
        + ConnectionManager() 
        + register(User) User
        + removeDevice(Device) boolean
        + getDevices() List~Device~
        + getLogEntries(Device) List~LogEntry~
        + removeLogEntry(LogEntry) boolean
        + createDevice(Device) Device
        + login(User) User
    }

    IManager --> DeviceManager
    IManager --> UserManager
    IManager --> LogManager


    ConnectionManager  ..>  DeviceManager : «create»
    ConnectionManager  ..>  LogManager : «create»
    ConnectionManager  ..>  UserManager : «create»