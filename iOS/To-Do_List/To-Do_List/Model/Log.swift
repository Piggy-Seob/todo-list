//
//  Log.swift
//  To-Do_List
//
//  Created by Kai Kim on 2022/04/11.
//

import Foundation

struct History : Codable{
    let logs : [Log]
}


struct Log: Codable {
    let author : String
    let content: String
    let lastModifiedAt: String
}
