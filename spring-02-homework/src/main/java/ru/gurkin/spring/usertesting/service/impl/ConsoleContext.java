/**
 * 
 */
package ru.gurkin.spring.usertesting.service.impl;

import java.io.InputStream;
import java.io.PrintStream;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author digurkin
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ConsoleContext {
    private PrintStream out = System.out;
    private InputStream in = System.in;
}
